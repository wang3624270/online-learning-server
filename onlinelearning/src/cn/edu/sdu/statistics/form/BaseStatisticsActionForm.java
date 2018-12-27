package cn.edu.sdu.statistics.form;

import java.util.Date;

import cn.edu.sdu.common.form.UForm;

public class BaseStatisticsActionForm extends UForm{
	private Integer courseId;// �γ�ID
	private String courseNum;// �γ̺�
	private String courseName;// �γ���
	private String courseEngName;// Ӣ�Ŀγ���
	private String courseType;// 2016�¿γ����  NKCSXM  1 ������  0 �ǹ�����
	private String teachingWayCode;// ��ѧ��ʽ �����ֵ�JXFSM
	private String examType;// ���˷�ʽ �����ֵ�KHFSM  ��������
	private String teachLanguage;// �ڿ����� �����ֵ�SKYYLXM  ��������
	private Double credit;// ѧ��
	private Integer classHour;// ѧʱ
	private Integer termCount;// ѧʱ��ѧ�ڣ�
	private Integer teachingHour;// �Ͽ�ѧʱ
	private Integer experimentHour;// ʵ��ѧʱ
	private Integer onComputerHour;// �ϻ�ѧʱ
	private Integer collegeId;// ����Ժϵ
	private Integer collegeId1;// ����Ժϵ
	private String teachGroup;// ��ʦ��
	private String book;// �̲�
	private String reference;// �ο���
	private String briefIntroduction;// �γ̼��  
	private String englishBriefIntroduction;
	private String remark;// ��ע
	private Date createTime;
	private Date modifyTime;
	private Integer modifyerId;
	private String subSpec;// �γ�˵��
	private String collegeName;
	private String teacherName;
	private String process;
	private Integer taskId;
	private Integer termId;
    private String termName;
    private String termEngName;
    private Date startTime;
    private Date endTime;
    private String examTime;
    private String termNum;
    private String termType;
    private Integer isVisible;
    private String startDate;
    private String endDate;
    private String taskName;
    private Integer accId;
    private Integer id;//��ԴId
	private String accName;//��Դ����
	private String accUrl;//��Դ·��
	private String accType;
	private Integer accUploader;
	private Date uploadTime;
	private String fileType;//�ļ�����
	private Long fileSize;//�ļ�����
	private String uploader;
	private String uploadDate;
	private Integer sectionId;// �ڴ�ID
	private String sectionName;//�ڴ�����
	private Integer orderNum;//˳��
	private Integer personId;//������
	private String matchType;
	private String folderMap;
	private String sectionMap;
	private Integer newsId;
	private String titile;//���ű���
	private String content;//��������
	private Integer newsNum;
	private String createTimeStr;
	private String introduction;//����
	private Double courseScore;
	private String courseScoreStr;
	private Integer number;
	private String perName;
	private String perNum;
	private Integer questionNum;
	private Integer answerNum;
	private Integer unanswerNum;
	private Integer questionId;
	private String state;
	private Integer coverImgAcc;
	private String coverImg;
	private String comment;
	private Integer commentId;
	private String startTimeStr;
	private String endTimeStr;
	private String label;
	private String value;
	private Integer videoAcc;
	private Integer audioAcc;
	private Integer pptAcc;
	private Integer videoId;
	private Integer audioId;
	private Integer pptId;
	private String folderName;
	private Integer folderId;
	private String loginName;
	private String mobilePhone;
	private String perTypeCode;
	private String title;
	private String answer;
	private String question;
	private Integer practiceId;
	private Integer practiceAcc;
	private Integer nextSectionId;
	private String isFinish;
	private Double completeDegree;
	private Integer time;
    private Integer sectionIdNode;
	private Integer chargeId;
	private Double price;
	private Integer payId;
	private String payMode;
	private String payNumber;
	private Integer planId;
	private Double amount;
	private Integer examScore;
	private String scoreLevel;
	private String name;
	private Double homeworkScore;
	private Double activityScore;
	private String activityName;
	private String subject;
	

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Double getHomeworkScore() {
		return homeworkScore;
	}
	public void setHomeworkScore(Double homeworkScore) {
		this.homeworkScore = homeworkScore;
	}
	public Double getActivityScore() {
		return activityScore;
	}
	public void setActivityScore(Double activityScore) {
		this.activityScore = activityScore;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScoreLevel() {
		return scoreLevel;
	}
	public void setScoreLevel(String scoreLevel) {
		this.scoreLevel = scoreLevel;
	}
	public Integer getExamScore() {
		return examScore;
	}
	public void setExamScore(Integer examScore) {
		this.examScore = examScore;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getCourseNum() {
		return courseNum;
	}
	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseEngName() {
		return courseEngName;
	}
	public void setCourseEngName(String courseEngName) {
		this.courseEngName = courseEngName;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getTeachingWayCode() {
		return teachingWayCode;
	}
	public void setTeachingWayCode(String teachingWayCode) {
		this.teachingWayCode = teachingWayCode;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getTeachLanguage() {
		return teachLanguage;
	}
	public void setTeachLanguage(String teachLanguage) {
		this.teachLanguage = teachLanguage;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public Integer getClassHour() {
		return classHour;
	}
	public void setClassHour(Integer classHour) {
		this.classHour = classHour;
	}
	public Integer getTermCount() {
		return termCount;
	}
	public void setTermCount(Integer termCount) {
		this.termCount = termCount;
	}
	public Integer getTeachingHour() {
		return teachingHour;
	}
	public void setTeachingHour(Integer teachingHour) {
		this.teachingHour = teachingHour;
	}
	public Integer getExperimentHour() {
		return experimentHour;
	}
	public void setExperimentHour(Integer experimentHour) {
		this.experimentHour = experimentHour;
	}
	public Integer getOnComputerHour() {
		return onComputerHour;
	}
	public void setOnComputerHour(Integer onComputerHour) {
		this.onComputerHour = onComputerHour;
	}
	public Integer getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	public Integer getCollegeId1() {
		return collegeId1;
	}
	public void setCollegeId1(Integer collegeId1) {
		this.collegeId1 = collegeId1;
	}
	public String getTeachGroup() {
		return teachGroup;
	}
	public void setTeachGroup(String teachGroup) {
		this.teachGroup = teachGroup;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}
	public String getEnglishBriefIntroduction() {
		return englishBriefIntroduction;
	}
	public void setEnglishBriefIntroduction(String englishBriefIntroduction) {
		this.englishBriefIntroduction = englishBriefIntroduction;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getModifyerId() {
		return modifyerId;
	}
	public void setModifyerId(Integer modifyerId) {
		this.modifyerId = modifyerId;
	}
	public String getSubSpec() {
		return subSpec;
	}
	public void setSubSpec(String subSpec) {
		this.subSpec = subSpec;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getTermId() {
		return termId;
	}
	public void setTermId(Integer termId) {
		this.termId = termId;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	public String getTermEngName() {
		return termEngName;
	}
	public void setTermEngName(String termEngName) {
		this.termEngName = termEngName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public String getTermNum() {
		return termNum;
	}
	public void setTermNum(String termNum) {
		this.termNum = termNum;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public Integer getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getAccId() {
		return accId;
	}
	public void setAccId(Integer accId) {
		this.accId = accId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccUrl() {
		return accUrl;
	}
	public void setAccUrl(String accUrl) {
		this.accUrl = accUrl;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public Integer getAccUploader() {
		return accUploader;
	}
	public void setAccUploader(Integer accUploader) {
		this.accUploader = accUploader;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getFolderMap() {
		return folderMap;
	}
	public void setFolderMap(String folderMap) {
		this.folderMap = folderMap;
	}
	public String getSectionMap() {
		return sectionMap;
	}
	public void setSectionMap(String sectionMap) {
		this.sectionMap = sectionMap;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getNewsNum() {
		return newsNum;
	}
	public void setNewsNum(Integer newsNum) {
		this.newsNum = newsNum;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Double getCourseScore() {
		return courseScore;
	}
	public void setCourseScore(Double courseScore) {
		this.courseScore = courseScore;
	}
	public String getCourseScoreStr() {
		return courseScoreStr;
	}
	public void setCourseScoreStr(String courseScoreStr) {
		this.courseScoreStr = courseScoreStr;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getPerName() {
		return perName;
	}
	public void setPerName(String perName) {
		this.perName = perName;
	}
	public String getPerNum() {
		return perNum;
	}
	public void setPerNum(String perNum) {
		this.perNum = perNum;
	}
	public Integer getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}
	public Integer getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}
	public Integer getUnanswerNum() {
		return unanswerNum;
	}
	public void setUnanswerNum(Integer unanswerNum) {
		this.unanswerNum = unanswerNum;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getCoverImgAcc() {
		return coverImgAcc;
	}
	public void setCoverImgAcc(Integer coverImgAcc) {
		this.coverImgAcc = coverImgAcc;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getVideoAcc() {
		return videoAcc;
	}
	public void setVideoAcc(Integer videoAcc) {
		this.videoAcc = videoAcc;
	}
	public Integer getAudioAcc() {
		return audioAcc;
	}
	public void setAudioAcc(Integer audioAcc) {
		this.audioAcc = audioAcc;
	}
	public Integer getPptAcc() {
		return pptAcc;
	}
	public void setPptAcc(Integer pptAcc) {
		this.pptAcc = pptAcc;
	}
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	public Integer getAudioId() {
		return audioId;
	}
	public void setAudioId(Integer audioId) {
		this.audioId = audioId;
	}
	public Integer getPptId() {
		return pptId;
	}
	public void setPptId(Integer pptId) {
		this.pptId = pptId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public Integer getFolderId() {
		return folderId;
	}
	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getPerTypeCode() {
		return perTypeCode;
	}
	public void setPerTypeCode(String perTypeCode) {
		this.perTypeCode = perTypeCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getPracticeId() {
		return practiceId;
	}
	public void setPracticeId(Integer practiceId) {
		this.practiceId = practiceId;
	}
	public Integer getPracticeAcc() {
		return practiceAcc;
	}
	public void setPracticeAcc(Integer practiceAcc) {
		this.practiceAcc = practiceAcc;
	}
	public Integer getNextSectionId() {
		return nextSectionId;
	}
	public void setNextSectionId(Integer nextSectionId) {
		this.nextSectionId = nextSectionId;
	}
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	public Double getCompleteDegree() {
		return completeDegree;
	}
	public void setCompleteDegree(Double completeDegree) {
		this.completeDegree = completeDegree;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getSectionIdNode() {
		return sectionIdNode;
	}
	public void setSectionIdNode(Integer sectionIdNode) {
		this.sectionIdNode = sectionIdNode;
	}
	public Integer getChargeId() {
		return chargeId;
	}
	public void setChargeId(Integer chargeId) {
		this.chargeId = chargeId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getPayId() {
		return payId;
	}
	public void setPayId(Integer payId) {
		this.payId = payId;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getPayNumber() {
		return payNumber;
	}
	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}