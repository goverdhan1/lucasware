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
package com.lucas.entity.eai.transport;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lucas.entity.eai.event.EventHandler;

/**
 * Entity class for the transport domain, having details for the transport
 * mechanism to execute the event in the system.
 * 
 * @author Prafull
 * 
 */
@Entity
@Table(name = "eai_transport", uniqueConstraints = @UniqueConstraint(columnNames = "transport_name"))
public class Transport implements java.io.Serializable {

	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Transport id for uniquely identifying a transport mechanism
	 */
	private Long transportId;
	/**
	 * Property represents whether source file needs to be deleted after the
	 * processing.
	 */
	private Boolean deleteSourceFile;
	/**
	 * Host initiating the event.
	 */
	private String host;
	/**
	 * Inbound directory for looking the input event file.
	 */
	private String inboundDirectory;
	/**
	 * Password for connecting to the transport mechanism.
	 */
	private String password;
	/**
	 * Polling frequency for picking the event details.
	 */
	private Integer pollingFrequency;
	/**
	 * Port number to look for the event.
	 */
	private Integer portNumber;
	/**
	 * Remote directory for out bound event.
	 */
	private String remoteDirectory;
	/**
	 * Name of the transport mechanism.
	 */
	private String transportName;
	/**
	 * Type of the transport mechanism.
	 */
	private String transportType;
	/**
	 * Username for connecting to the transport mechanism.
	 */
	private String userName;
	private Set<EventHandler> eaiEventHandlers = new HashSet<EventHandler>();

	public Transport() {
	}

	public Transport(Boolean deleteSourceFile, String host,
			String inboundDirectory, String password, Integer pollingFrequency,
			Integer portNumber, String remoteDirectory, String transportName,
			String transportType, String userName,
			Set<EventHandler> eaiEventHandlers) {
		this.deleteSourceFile = deleteSourceFile;
		this.host = host;
		this.inboundDirectory = inboundDirectory;
		this.password = password;
		this.pollingFrequency = pollingFrequency;
		this.portNumber = portNumber;
		this.remoteDirectory = remoteDirectory;
		this.transportName = transportName;
		this.transportType = transportType;
		this.userName = userName;
		this.eaiEventHandlers = eaiEventHandlers;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "transport_id", unique = true, nullable = false)
	public Long getTransportId() {
		return this.transportId;
	}

	public void setTransportId(Long transportId) {
		this.transportId = transportId;
	}

	@Column(name = "delete_source_file")
	public Boolean getDeleteSourceFile() {
		return this.deleteSourceFile;
	}

	public void setDeleteSourceFile(Boolean deleteSourceFile) {
		this.deleteSourceFile = deleteSourceFile;
	}

	@Column(name = "host", length = 60)
	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column(name = "inbound_directory", length = 80)
	public String getInboundDirectory() {
		return this.inboundDirectory;
	}

	public void setInboundDirectory(String inboundDirectory) {
		this.inboundDirectory = inboundDirectory;
	}

	@Column(name = "password", length = 24)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "polling_frequency")
	public Integer getPollingFrequency() {
		return this.pollingFrequency;
	}

	public void setPollingFrequency(Integer pollingFrequency) {
		this.pollingFrequency = pollingFrequency;
	}

	@Column(name = "port_number")
	public Integer getPortNumber() {
		return this.portNumber;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	@Column(name = "remote_directory", length = 80)
	public String getRemoteDirectory() {
		return this.remoteDirectory;
	}

	public void setRemoteDirectory(String remoteDirectory) {
		this.remoteDirectory = remoteDirectory;
	}

	@Column(name = "transport_name", unique = true, length = 80)
	public String getTransportName() {
		return this.transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	@Column(name = "transport_type", length = 80)
	public String getTransportType() {
		return this.transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	@Column(name = "user_name", length = 80)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eaiTransport")
	public Set<EventHandler> getEaiEventHandlers() {
		return this.eaiEventHandlers;
	}

	public void setEaiEventHandlers(Set<EventHandler> eaiEventHandlers) {
		this.eaiEventHandlers = eaiEventHandlers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((transportId == null) ? 0 : transportId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transport other = (Transport) obj;
		if (transportId == null) {
			if (other.transportId != null)
				return false;
		} else if (!transportId.equals(other.transportId))
			return false;
		return true;
	}

}
