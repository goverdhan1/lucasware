package com.lucas.entity.health;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "LOG_LINE")
public class LogLine implements Serializable{

	private static final long serialVersionUID = 5671834819818948568L;
	private Long logLineId;
	private String timestampDecoration;
	private String relativeTimestamp;
	private String cpuUsagePercent;
	private String logRecordType;
	private String logSeverity;
	private String logData;
	
	@Column(name = "relative_timestamp")
	public String getRelativeTimestamp() {
		return relativeTimestamp;
	}
	public void setRelativeTimestamp(String relativeTimestamp) {
		this.relativeTimestamp = relativeTimestamp;
	}
	
	@Column(name = "cpu_usage_percent")
	public String getCpuUsagePercent() {
		return cpuUsagePercent;
	}
	public void setCpuUsagePercent(String cpuUsagePercent) {
		this.cpuUsagePercent = cpuUsagePercent;
	}
	
	@Column(name = "log_record_type")
	public String getLogRecordType() {
		return logRecordType;
	}
	public void setLogRecordType(String logRecordType) {
		this.logRecordType = logRecordType;
	}
	
	@Column(name = "log_severity")
	public String getLogSeverity() {
		return logSeverity;
	}
	public void setLogSeverity(String logSeverity) {
		this.logSeverity = logSeverity;
	}
	
	@Column(name = "log_data", length=2000)
	public String getLogData() {
		return logData;
	}
	public void setLogData(String logData) {
		this.logData = logData;
	}
	
	@Transient
	public String getTimestampDecoration() {
		return timestampDecoration;
	}
	public void setTimestampDecoration(String timestampDecoration) {
		this.timestampDecoration = timestampDecoration;
	}
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "log_line_id")
	public Long getLogLineId() {
		return logLineId;
	}
	public void setLogLineId(Long logLineId) {
		this.logLineId = logLineId;
	}
}
