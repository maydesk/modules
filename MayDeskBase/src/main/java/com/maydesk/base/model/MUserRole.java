/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.model;

import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_user_role")
public class MUserRole extends MBaseWithTitle {

	private MUser userRef;
	private String roleName;
	private String roleClass;
	private Set<MUser> deputies;
	private int contextId;
	private String contextClass;

	public String getRoleClass() {
		return roleClass;
	}

	public void setRoleClass(String roleClass) {
		this.roleClass = roleClass;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getContextId() {
		return contextId;
	}

	public void setContextId(int contextId) {
		this.contextId = contextId;
	}

	public String getContextClass() {
		return contextClass;
	}

	public void setContextClass(String contextClass) {
		this.contextClass = contextClass;
	}

	@ManyToOne(targetEntity = MUser.class, optional = false)
	public MUser getUserRef() {
		return userRef;
	}

	public void setUserRef(MUser userRef) {
		this.userRef = userRef;
	}

	@ManyToMany
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	public Set<MUser> getDeputies() {
		return deputies;
	}

	public void setDeputies(Set<MUser> deputies) {
		this.deputies = deputies;
	}

	@Override
	public String toString() {
		return roleName;
	}

	@Override
	public String createCachedTitle() {
		return userRef.getJabberId();
	}

	@Transient
	public void setContext(MBase context) {
		contextId = context.getId();
		contextClass = context.getClass().getCanonicalName();
	}

	@Transient
	public Enum getRole() {
		try {
			Class clazz = Class.forName(roleClass);
			Field term = clazz.getField(roleName);
			Enum role = (Enum) term.get(null);
			return role;
		} catch (Exception e) {
			System.out.println("NOT FOUND: " + roleClass + ":" + roleName);
			e.printStackTrace();
		}
		return null;
	}

	@Transient
	public void setRole(Enum role) {
		roleName = role.name();
		roleClass = role.getClass().getCanonicalName();
	}
}