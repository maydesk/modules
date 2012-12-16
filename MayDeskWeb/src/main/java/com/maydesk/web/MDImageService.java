

package com.maydesk.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import nextapp.echo.app.ImageReference;
import nextapp.echo.app.StreamImageReference;
import nextapp.echo.webcontainer.Connection;
import nextapp.echo.webcontainer.ContentType;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.SynchronizationException;
import nextapp.echo.webcontainer.UserInstance;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.sop.enums.SopMood;
import com.maydesk.base.util.ByteArrayImageReference;
import com.maydesk.base.util.CledaConnector;

public class MDImageService implements Service {

    /** <code>Service</code> identifier. */
    public static final String SERVICE_ID = "Echo.DVRImage"; 
    
    /** Singleton instance of this <code>Service</code>. */
    public static final MDImageService INSTANCE = new MDImageService();
    
    /** Image identifier URL parameter. */
    private static final String PARAMETER_IMAGE_UID = "dvrimg"; 

    /** URL parameters (used for creating URIs). */
    private static final String[] URL_PARAMETERS = new String[]{PARAMETER_IMAGE_UID}; 
    
    /**
     * @see nextapp.echo.webcontainer.Service#getId()
     */
    public String getId() {
        return SERVICE_ID;
    }
    
    /**
     * @see nextapp.echo.webcontainer.Service#getVersion()
     */
    public int getVersion() {
        return DO_NOT_CACHE; // Enable caching.
    }

    /**
     * Creates a URI to retrieve a specific image for a specific component 
     * from the server.
     * 
     * @param userInstance the relevant application user instance
     * @param imageId the unique id to retrieve the image from the
     *        <code>ContainerInstance</code>
     */
    public String createUri(UserInstance userInstance, String imageId) {
        return userInstance.getServiceUri(this, URL_PARAMETERS, new String[]{imageId});
    }

    /**
     * Renders the specified image to the given connection.
     * Implementations should set the response content type, and write image
     * data to the response <code>OutputStream</code>.
     * 
     * @param conn the <code>Connection</code> on which to render the image
     * @param imageReference the image to be rendered
     * @throws IOException if the image cannot be rendered
     */
    public void renderImage(Connection conn, ImageReference imageReference) throws IOException {
        if (imageReference instanceof ByteArrayImageReference) {
            renderStreamImage(conn, imageReference);
        } else {
            throw new SynchronizationException("Unsupported image type: " + imageReference.getClass().getName(), null);
        }
    }
    

    /**
     * Renders a <code>StreamImageReference</code>.
     * 
     * @param conn the <code>Connection</code> to which the image should be rendered
     * @param imageReference the image to render
     * @throws IOException
     */
    private void renderStreamImage(Connection conn, ImageReference imageReference) throws IOException {
        try {
            StreamImageReference streamImageReference = (StreamImageReference) imageReference;
            conn.setContentType(new ContentType(streamImageReference.getContentType(), true));
            streamImageReference.render(conn.getOutputStream());
        } catch (IOException ex) {
            // Internet Explorer appears to enjoy making half-hearted requests for images, wherein it resets the connection
            // leaving us with an IOException.  This exception is silently eaten.
            // It would preferable to only ignore SocketExceptions, however the API documentation does not provide
            // enough information to suggest that such a strategy would be adequate.
        }
    }
    
    /**
     * Gets the image with the specified id.
     * 
     * @param userInstance the <code>UserInstance</code> from which the image was requested
     * @param imageId the id of the image
     * @return the image if found, <code>null</code> otherwise.
     */
    public ImageReference getImage(int imageId) {
    	Session session = CledaConnector.getInstance().createSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(MMediaFile.class);
		criteria.add(Restrictions.eq("parentId", imageId));
		criteria.add(Restrictions.eq("fileName", SopMood.normal.name()));
		MMediaFile mediaFile = (MMediaFile)criteria.uniqueResult();
		ImageReference imageReference = null;
		if (mediaFile != null) {
			imageReference = new ByteArrayImageReference(mediaFile.getPreviewBytes(), mediaFile.getContentType(), 64);
		}
		//System.out.print(imageId + " :::: " + mediaFile);
		session.getTransaction().commit();
		session.close();
        return imageReference;
    }
    
    /**
     * @see nextapp.echo.webcontainer.Service#service(nextapp.echo.webcontainer.Connection)
     */
    public void service(Connection conn) throws IOException {
        String imageId = conn.getRequest().getParameter(PARAMETER_IMAGE_UID);
        if (imageId == null) {
            serviceBadRequest(conn, "Image UID not specified.");
            return;
        }
        int imgId = Integer.parseInt(imageId);
        ImageReference imageReference = getImage(imgId);
        if (imageReference == null) {
            serviceBadRequest(conn, "Image UID is not valid.");
            return;
        }
        renderImage(conn, imageReference);
    }
    
    /**
     * Services a bad request.
     * 
     * @param conn the <code>Connection</code>
     * @param message the error message
     */
    private void serviceBadRequest(Connection conn, String message) {
        conn.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
        conn.setContentType(ContentType.TEXT_PLAIN);
        conn.getWriter().write(message);
    }
}
