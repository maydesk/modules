/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************//**
 * @class draw2d.layout.anchor.ChopboxConnectionAnchor
 * 
 * The ChopboxAnchor's location is found by calculating the intersection of a
 * line drawn from the center point of its owner's box (the parent of the
 * connection port) to a reference point on that box. A Connection using the
 * ChopBoxAnchor will be oriented such that they point to their port owner's
 * center.
 * 
 * 
 * @inheritable
 * @author Andreas Herz
 * @extends draw2d.layout.anchor.ConnectionAnchor
 */
draw2d.layout.anchor.ChopboxConnectionAnchor = draw2d.layout.anchor.ConnectionAnchor.extend({

	NAME : "draw2d.layout.anchor.ChopboxConnectionAnchor",

	/**
	 * @constructor
	 * 
	 * @param {draw2d.Figure} [owner] the figure to use for the anchor calculation
	 */
	init : function(owner) {
		this._super(owner);
	},

	/**
	 * @method
	 * 
	 * Returns the location where the Connection should be anchored in
	 * absolute coordinates. The anchor may use the given reference
	 * Point to calculate this location.
	 * 
	 * @param reference The reference Point in absolute coordinates
	 * @return The anchor's location
	 */
	getLocation : function(reference) {
		var r = new draw2d.geo.Rectangle(0,0);
		r.setBounds(this.getBox());
		r.translate(-1, -1);
		r.resize(1, 1);

		var centerX = r.x + r.w / 2;
		var centerY = r.y + r.h / 2;

		if (r.isEmpty()	|| (reference.x == centerX && reference.y == centerY))
			return new draw2d.geo.Point(centerX, centerY); // This avoids divide-by-zero

		var dx = reference.x - centerX;
		var dy = reference.y - centerY;

		// r.width, r.height, dx, and dy are guaranteed to be non-zero.
		var scale = 0.5 / Math.max(Math.abs(dx) / r.w, Math.abs(dy)	/ r.h);

		dx *= scale;
		dy *= scale;
		centerX += dx;
		centerY += dy;

		return new draw2d.geo.Point(Math.round(centerX), Math.round(centerY));
	},

	/**
	 * Returns the bounds of this Anchor's owner. Subclasses can
	 * override this method to adjust the box. Maybe you return the box
	 * of the port parent (the parent figure)
	 * 
	 * @return The bounds of this Anchor's owner
	 */
	getBox : function() {
		return this.getOwner().getParent().getBoundingBox();
	},

	/**
	 * @method
	 * 
	 * Returns the bounds of this Anchor's owner. Subclasses can
	 * override this method to adjust the box. Maybe you return the box
	 * of the port parent (the parent figure)
	 * 
	 * @return The bounds of this Anchor's owner
	 */
	getReferencePoint : function() {
		return this.getBox().getCenter();
	}
});
