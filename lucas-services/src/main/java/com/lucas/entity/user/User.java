/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED

 */
package com.lucas.entity.user;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.lucas.entity.BaseEntity;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucas.entity.reporting.Printable;
import com.lucas.entity.support.Identifiable;
import com.lucas.entity.support.MultiEditable;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "lucas_user")
public class User extends BaseEntity implements Identifiable<Long>, Printable, MultiEditable<User>, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4569701306299696059L;
	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String plainTextPassword;
	private String hashedPassword;
	private String emailAddress;
	private String title;
	private String mobileNumber;
	private String employeeNumber;
	private String hostLogin;
	private String hostPassword;
    private String hostHashedPassword;
    private Date startDate;
	private Date birthDate;
	
    private String skill;
	private byte[] voiceModel;
	private Boolean retrainVoiceModel = Boolean.FALSE;
	private String currentWorkType;
	private Long homeCanvasId;
	private Canvas activeCanvas;
	private Boolean seeHomeCanvasIndicator = Boolean.FALSE;
	private Boolean deletedIndicator = Boolean.FALSE;

    private SupportedLanguage u2jLanguage;
    private SupportedLanguage j2uLanguage;
    private SupportedLanguage amdLanguage;
    private SupportedLanguage hhLanguage;


    private Shift shift;
	private WmsUser wmsUser;
	private List<OpenUserCanvas> openUserCanvases;

	// This two properties are for the benchmark project
	private boolean authenticated;
	private Boolean enable = Boolean.TRUE;
	private boolean needsAuthentication;
	private List<PermissionGroup> userPermissionGroupList = new ArrayList<PermissionGroup>();
	private Set<Permission> permissionsSet = new HashSet<Permission>();

    private UserPreferences userPreferences;


	public User() {
	}

	@Transient
	public Set<Permission> getPermissionsSet() {
		return permissionsSet;
	}

	@Override
	@JsonIgnore
	@Transient
	public Long getId() {
		return this.userId;
	}

	@Override
	public void setId(Long id) {
		this.userId = id;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "user_id")
	@JsonProperty("id")
	@JsonIgnore
	public Long getUserId() {
		return userId;
	}

	@Column(name = "first_name", length = 100)
	public String getFirstName() {
		return firstName;
	}

	@Column(name = "last_name", length = 100)
	public String getLastName() {
		return lastName;
	}

	@Column(name = "username", unique = true, length = 100, nullable = false)
	@Size(min = 1, max = 200)
	public String getUserName() {
		return userName;
	}

	@Column(name = "hashed_password", length = 500)
	@JsonIgnore
	public String getHashedPassword() {
		return hashedPassword;
	}

	@Email
	@Size(min = 6, max = 200)
	@Column(name = "email_address", length = 100)
	public String getEmailAddress() {
		return emailAddress;
	}

	@JsonIgnore
	@Transient
	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	@Column(name = "enable", length = 1)
	public Boolean isEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Transient
	@JsonIgnore
	public boolean isNeedsAuthentication() {
		return needsAuthentication;
	}

	public void setNeedsAuthentication(boolean needsAuthentication) {
		this.needsAuthentication = needsAuthentication;
	}

	@Transient
	@JsonIgnore
	//@NotNull
	public String getPlainTextPassword() {
		return plainTextPassword;
	}

	public void setPlainTextPassword(String plainTextPassword) {
		this.plainTextPassword = plainTextPassword;
	}

    @Transient
    @JsonIgnore
    public String getHostLogin() {
        return hostLogin;
    }

    public void setHostLogin(String hostLogin) {
        this.hostLogin = hostLogin;
    }

    @Transient
    @JsonIgnore
    public String getHostPassword() {
        return hostPassword;
    }

    public void setHostPassword(String hostPassword) {
        this.hostPassword = hostPassword;
    }


    @Transient
    @JsonIgnore
    public String getHostHashedPassword() {
        return hostHashedPassword;
    }

    public void setHostHashedPassword(String hostHashedPassword) {
        this.hostHashedPassword = hostHashedPassword;
    }


    // FK
	@ManyToMany(targetEntity = PermissionGroup.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JoinTable(name = "lucas_user_permission_group", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "permission_group_id") })
	public List<PermissionGroup> getUserPermissionGroupList() {
		return userPermissionGroupList;
	}

	public void setPermissionsSet(Set<Permission> permissionsSet) {
		this.permissionsSet.addAll(permissionsSet);
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public void setEmailAddress(String emailAddress) {
        // allow blank or empty email address
        if (emailAddress != null && emailAddress.length() > 0) {
            this.emailAddress = emailAddress;
        }
        else
        {
            this.emailAddress = null;
        }
	}

	public void setUserPermissionGroupList(
			List<PermissionGroup> userPermissionGroupList) {
		this.userPermissionGroupList = userPermissionGroupList;
	}



    public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof User)) {
			return false;
		}
		User castOther = (User) other;
		return new EqualsBuilder().append(userName, castOther.userName)
				.append(emailAddress, castOther.emailAddress).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(userName).append(emailAddress)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("userName", userName)
				.append("emailAddress", emailAddress).toString();
	}

	@Column(name = "title", length = 45)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "employee_number", length = 100)
	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "birth_date")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


    @Column(name = "mobile_number", length = 45)
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "skill", nullable = false, length = 50)
	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@Lob
	@Column(name = "voice_model", nullable = true, columnDefinition = "mediumblob")
	public byte[] getVoiceModel() {
		return voiceModel;
	}

	public void setVoiceModel(byte[] voiceModel) {
		this.voiceModel = voiceModel;
	}

	@Column(name = "retrain_voice_model", length = 1)
	public Boolean getRetrainVoiceModel() {
		return retrainVoiceModel;
	}

	public void setRetrainVoiceModel(Boolean retrainVoiceModel) {
		this.retrainVoiceModel = retrainVoiceModel;
	}

	@Column(name = "current_work_type", length = 10)
	public String getCurrentWorkType() {
		return currentWorkType;
	}

	public void setCurrentWorkType(String currentWorkType) {
		this.currentWorkType = currentWorkType;
	}

	@Column(name = "home_canvas_id")
	public Long getHomeCanvasId() {
		return homeCanvasId;
	}

	public void setHomeCanvasId(Long homeCanvasId) {
		this.homeCanvasId = homeCanvasId;
	}

	@ManyToOne
    @JoinColumn(name = "active_canvas_id") 
	@Cascade(value={org.hibernate.annotations.CascadeType.ALL})
	public Canvas getActiveCanvas() {
		return activeCanvas;
	}

	public void setActiveCanvas(Canvas activeCanvas) {
		this.activeCanvas = activeCanvas;
	}

	@Column(name = "see_home_canvas_indicator", length = 1, nullable = true)
	public Boolean getSeeHomeCanvasIndicator() {
		return seeHomeCanvasIndicator;
	}

	public void setSeeHomeCanvasIndicator(Boolean seeHomeCanvasIndicator) {
		this.seeHomeCanvasIndicator = seeHomeCanvasIndicator;
	}

	@Column(name = "deleted_indicator", length = 1)
	public Boolean getDeletedIndicator() {
		return deletedIndicator;
	}

	public void setDeletedIndicator(Boolean deletedIndicator) {
		this.deletedIndicator = deletedIndicator;
	}

	@ManyToOne
	@JoinColumn(name = "shift_id")
    public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

    // note that support_languages are seed and should not remove
    // the language record when the user is removed
    @OneToOne
    @JoinColumn(name = "u2jLanguage")
    public SupportedLanguage getU2jLanguage() { return u2jLanguage; }

    public void setU2jLanguage(SupportedLanguage u2jLanguage) {
        this.u2jLanguage = u2jLanguage;
    }

    @OneToOne
    @JoinColumn(name = "j2uLanguage")
    public SupportedLanguage getJ2uLanguage() { return j2uLanguage; }

    public void setJ2uLanguage(SupportedLanguage j2uLanguage) {
        this.j2uLanguage = j2uLanguage;
    }

    @OneToOne
    @JoinColumn(name = "hhLanguage")
    public SupportedLanguage getHhLanguage() { return hhLanguage; }

    public void setHhLanguage(SupportedLanguage hhLanguage) {
        this.hhLanguage = hhLanguage;
    }

    @OneToOne
    @JoinColumn(name = "amdLanguage")
    public SupportedLanguage getAmdLanguage() { return amdLanguage; }

    public void setAmdLanguage(SupportedLanguage amdLanguage) {
        this.amdLanguage = amdLanguage;
    }


    @OneToOne
	@JoinColumn(name = "user_id")
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	public WmsUser getWmsUser() {
		return wmsUser;
	}
    
	public void setWmsUser(WmsUser wmsUser) {
		this.wmsUser = wmsUser;
	}

	@Embedded
	@ElementCollection
	@JoinTable(name = "open_user_canvas")
	public List<OpenUserCanvas> getOpenUserCanvases() {
		return openUserCanvases;
	}

	public void setOpenUserCanvases(List<OpenUserCanvas> openUserCanvases) {
		this.openUserCanvases = openUserCanvases;
	}


    @Embedded
    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }


    @Transient
	@Override
	public List<String> getHeaderElements() {

		final List userHeaderList = new ArrayList<String>() {
			{
				add(getHeaderText("userId"));
				add(getHeaderText("firstName"));
				add(getHeaderText("lastName"));
				add(getHeaderText("userName"));
				add(getHeaderText("emailAddress"));
				add(getHeaderText("title"));
				add(getHeaderText("mobileNumber"));
				add(getHeaderText("employeeNumber"));
				add(getHeaderText("startDate"));
				add(getHeaderText("birthDate"));
				add(getHeaderText("amdLanguage"));
				add(getHeaderText("j2uLanguage"));
				add(getHeaderText("u2jLanguage"));
				add(getHeaderText("hhLanguage"));
				add(getHeaderText("enable"));
				add(getHeaderText("skill"));
				add(getHeaderText("shiftId"));
				add(getHeaderText("retrainVoiceModel"));
				add(getHeaderText("currentWorkType"));
				add(getHeaderText("homeCanvasId"));
				add(getHeaderText("activeCanvasId"));
				add(getHeaderText("seeHomeCanvasIndicator"));
				add(getHeaderText("createByUsername"));
				add(getHeaderText("createdDateTime"));
				add(getHeaderText("updatedByUsername"));
				add(getHeaderText("updatedDateTime"));
				add(getHeaderText("deletedIndicator"));
				add(getHeaderText("userPermissionGroupList"));
				add(getHeaderText("permissionsSet"));
				add(getHeaderText("favoriteCanvasList"));
				add(getHeaderText("savedCanvasList"));
				add(getHeaderText("companyCanvasList"));
			}
		};
		return userHeaderList;
	}

	@Transient
	@Override
	public List<String> getMultiEditableFieldsList() throws ParseException {

		final List<String> userMultiEditableFieldsList = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;

			{
				//add("firstName");
				//add("lastName");
				//add("emailAddress");
				//add("title");
				//add("mobileNumber");
				// add("employeeNumber"); // disabled
				//add("startDate");
				//add("birthDate");
				add("amdLanguage");
				add("j2uLanguage");
				add("u2jLanguage");
				add("hhLanguage");
				//add("enable");
				add("skill");
				//add("retrainVoiceModel");
				//add("homeCanvasId");
				//add("seeHomeCanvasIndicator");

			}
		};
		return userMultiEditableFieldsList;
	}

	@Transient
	@Override
	public List<String> getDataElements() {

		final List<String> userDataList = new ArrayList<String>() {
			{
				add((userId != null ? userId.toString() : ""));
				add((firstName != null ? firstName.toString() : ""));
				add((lastName != null ? lastName.toString() : ""));
				add((userName != null ? userName.toString() : ""));
				add((emailAddress != null ? emailAddress.toString() : ""));
				add((title != null ? title.toString() : ""));
				add((mobileNumber != null ? mobileNumber.toString() : ""));
				add((employeeNumber != null ? employeeNumber.toString() : ""));
				add((startDate != null ? startDate.toString() : ""));
				add((birthDate != null ? birthDate.toString() : ""));
				add((amdLanguage != null ? amdLanguage.toString() : ""));
				add((j2uLanguage != null ? j2uLanguage.toString() : ""));
				add((u2jLanguage != null ? u2jLanguage.toString() : ""));
				add((hhLanguage != null ? hhLanguage.toString() : ""));
				add((isEnable() == true ? "1" : "0"));
				add((skill != null ? skill : ""));
				add((shift != null ? shift.toString() : ""));
				add((retrainVoiceModel == true ? "1" : "0"));
				add((currentWorkType != null ? currentWorkType : ""));
				add((homeCanvasId != null ? homeCanvasId.toString() : ""));
				add((activeCanvas != null ? activeCanvas.toString() : ""));
				add((seeHomeCanvasIndicator != null ? seeHomeCanvasIndicator.toString() : ""));
				add((getCreatedByUserName() != null ? getCreatedByUserName() : ""));
				add((getCreatedDateTime() != null ? getCreatedDateTime().toString() : ""));
				add((getUpdatedByUserName() != null ? getUpdatedByUserName() : ""));
				add((getUpdatedDateTime() != null ? getUpdatedDateTime().toString() : ""));
				add((deletedIndicator == true ? "1" : "0"));
			}
		};

		final StringBuilder permissionGroupBuilder = new StringBuilder();
		for (PermissionGroup permissionGroup : this
				.getUserPermissionGroupList()) {
			permissionGroupBuilder.append(permissionGroup
					.getPermissionGroupId());
			permissionGroupBuilder.append(" ,");
			permissionGroupBuilder.append(permissionGroup
					.getPermissionGroupName());
			permissionGroupBuilder.append(" | ");
		}
		userDataList
				.add((permissionGroupBuilder != null ? permissionGroupBuilder
						.toString() : ""));

		final StringBuilder permissionsSetBuilder = new StringBuilder();
		final Iterator permissionIterator = getPermissionsSet().iterator();
		while (permissionIterator.hasNext()) {
			final Permission permission = (Permission) permissionIterator
					.next();
			permissionsSetBuilder.append(permission.getPermissionId());
			permissionsSetBuilder.append(" ,");
			permissionsSetBuilder.append(permission.getPermissionName());
			permissionsSetBuilder.append(" ,");
			permissionsSetBuilder.append(permission.getPermissionCategory());
			permissionsSetBuilder.append(" | ");
		}
		userDataList.add((permissionsSetBuilder != null ? permissionsSetBuilder
				.toString() : ""));
		return userDataList;
	}

	@Transient
	private String getHeaderText(String name) {
		final String heading[] = StringUtils
				.splitByCharacterTypeCamelCase(name);
		final StringBuilder colHeading = new StringBuilder();
		for (String word : heading) {
			colHeading.append(" " + (StringUtils.capitalize(word)));
		}
		return colHeading.toString();
	}

}
