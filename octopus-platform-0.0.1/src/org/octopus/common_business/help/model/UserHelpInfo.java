package org.octopus.common_business.help.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "user_help_info")
public class UserHelpInfo {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer helpId;
		private String perType;
		private String helpNum;
		private String helpName;
		private String studyLevel;
		private String content;
		private String contentApp;
		private Date modifyTime;
		
		public Integer getHelpId() {
			return helpId;
		}
		public void setHelpId(Integer helpId) {
			this.helpId = helpId;
		}
		public String getPerType() {
			return perType;
		}
		public void setPerType(String perType) {
			this.perType = perType;
		}
		public String getHelpNum() {
			return helpNum;
		}
		public void setHelpNum(String helpNum) {
			this.helpNum = helpNum;
		}
		public String getHelpName() {
			return helpName;
		}
		public void setHelpName(String helpName) {
			this.helpName = helpName;
		}
		public String getStudyLevel() {
			return studyLevel;
		}
		public void setStudyLevel(String studyLevel) {
			this.studyLevel = studyLevel;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getContentApp() {
			return contentApp;
		}
		public void setContentApp(String contentApp) {
			this.contentApp = contentApp;
		}
		public Date getModifyTime() {
			return modifyTime;
		}
		public void setModifyTime(Date modifyTime) {
			this.modifyTime = modifyTime;
		}
}
