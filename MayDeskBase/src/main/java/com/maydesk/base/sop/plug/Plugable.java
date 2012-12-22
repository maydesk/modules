/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.sop.plug;

/**
 * @author chrismay
 */
public @interface Plugable {

	public String plugTitle();

	public SopSocketType plugType();

	public Class modelClass() default java.lang.Object.class;

	public Class editorClass() default java.lang.Object.class;

	public Class configEditorClass() default java.lang.Object.class;

	public String icon() default "";

}
