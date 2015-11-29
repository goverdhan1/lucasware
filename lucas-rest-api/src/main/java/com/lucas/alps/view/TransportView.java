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
package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.EventHandlerDetailView;
import com.lucas.entity.eai.transport.Transport;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/25/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class TransportView {

    private Transport transport;

    public TransportView() {
        this.transport = new Transport();
    }

    public TransportView(Transport transport) {
        this.transport = transport;
    }

    @JsonView({EventHandlerDetailView.class})
    public Long getTransportId() {
        if (transport != null) {
            return this.transport.getTransportId();
        }
        return null;
    }

    public void setTransportId(Long transportId) {
        if (transport != null) {
            this.transport.setTransportId(transportId);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public Boolean getDeleteSourceFile() {
        if (transport != null) {
            return this.transport.getDeleteSourceFile();
        }
        return null;
    }

    public void setDeleteSourceFile(Boolean deleteSourceFile) {
        if (transport != null) {
            this.transport.setDeleteSourceFile(deleteSourceFile);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public String getHost() {
        if (transport != null) {
            return this.transport.getHost();
        }
        return null;
    }

    public void setHost(String host) {
        if (transport != null) {
            this.transport.setHost(host);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public String getInboundDirectory() {
        if (transport != null) {
            return this.transport.getInboundDirectory();
        }
        return null;
    }

    public void setInboundDirectory(String inboundDirectory) {
        if (transport != null) {
            this.transport.setInboundDirectory(inboundDirectory);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public String getPassword() {
        if (transport != null) {
            return this.transport.getPassword();
        }
        return null;
    }

    public void setPassword(String password) {
        if (transport != null) {
            this.transport.setPassword(password);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public Integer getPollingFrequency() {
        if (transport != null) {
            return this.transport.getPollingFrequency();
        }
        return null;
    }

    public void setPollingFrequency(Integer pollingFrequency) {
        if (transport != null) {
            this.transport.setPollingFrequency(pollingFrequency);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public Integer getPortNumber() {
        if (transport != null) {
            return this.transport.getPortNumber();
        }
        return null;
    }

    public void setPortNumber(Integer portNumber) {
        if (transport != null) {
            this.transport.setPortNumber(portNumber);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public String getRemoteDirectory() {
        if (transport != null) {
            return this.transport.getRemoteDirectory();
        }
        return null;
    }

    public void setRemoteDirectory(String remoteDirectory) {
        if (transport != null) {
            this.transport.setRemoteDirectory(remoteDirectory);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public String getTransportName() {
        if (transport != null) {
            return this.transport.getTransportName();
        }
        return null;
    }

    public void setTransportName(String transportName) {
        if (transport != null) {
            this.transport.setTransportName(transportName);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public String getTransportType() {
        if (transport != null) {
            return this.transport.getTransportType();
        }
        return null;
    }

    public void setTransportType(String transportType) {
        if (transport != null) {
            this.transport.setTransportType(transportType);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public String getUserName() {
        if (transport != null) {
            return this.transport.getUserName();
        }
        return null;
    }

    public void setUserName(String userName) {
        if (transport != null) {
            this.transport.setUserName(userName);
        }
    }

    @JsonIgnore
    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
