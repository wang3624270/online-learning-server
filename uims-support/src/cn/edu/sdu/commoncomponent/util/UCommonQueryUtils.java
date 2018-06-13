package cn.edu.sdu.commoncomponent.util;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.commoncomponent.constants.CommonComponentConstants;
import cn.edu.sdu.commoncomponent.form.CommonBaseDataQueryForm;
import cn.edu.sdu.commoncomponent.form.CommonQueryForm;
import cn.edu.sdu.commoncomponent.form.CommonRoomQueryForm;
import cn.edu.sdu.commoncomponent.form.ExtendItemObject;
import cn.edu.sdu.uims.component.combobox.UComboBoxI;
import cn.edu.sdu.uims.service.UFactory;

public class UCommonQueryUtils {

	public static int getLayoutType(HashMap parameters) {
		if (parameters == null || parameters.get("layout") == null)
			return FlowLayout.LEFT;
		else if (parameters.get("layout").toString().equals("right"))
			return FlowLayout.RIGHT;
		else if (parameters.get("layout").toString().equals("center"))
			return FlowLayout.CENTER;
		else
			return FlowLayout.LEFT;
	}

	public static String getAreaLael(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("areaEnLable") == null) {
				return "Area";
			} else if (parameters.get("areaEnLable").toString().equals(""))
				return null;
			else
				return parameters.get("areaEnLable").toString();

		} else {
			if (parameters == null || parameters.get("areaLable") == null) {
				return "地区";
			} else if (parameters.get("areaLable").toString().equals(""))
				return null;
			else
				return parameters.get("areaLable").toString();
		}
	}

	public static String getCampusLael(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("campusEnLable") == null) {
				return "Campus";
			} else if (parameters.get("campusEnLable").toString().equals(""))
				return null;
			else
				return parameters.get("campusEnLable").toString();

		} else {
			if (parameters == null || parameters.get("campusLable") == null) {
				return "校区";
			} else if (parameters.get("campusLable").toString().equals(""))
				return null;
			else
				return parameters.get("campusLable").toString();
		}
	}

	public static String getFacultyLael(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("facultyEnLable") == null) {
				return "Facult";
			} else if (parameters.get("facultyEnLable").toString().equals(""))
				return null;
			else
				return parameters.get("facultyEnLable").toString();
		} else {
			if (parameters == null || parameters.get("facultyLable") == null) {
				return "学部";
			} else if (parameters.get("facultyLable").toString().equals(""))
				return null;
			else
				return parameters.get("facultyLable").toString();
		}
	}

	public static String getCollegeTypeLable(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("collegeTypeEnLable") == null) {
				return "CollegeType";
			} else if (parameters.get("collegeTypeEnLable").toString().equals(""))
				return null;
			else
				return parameters.get("collegeTypeEnLable").toString();
		} else {
			if (parameters == null || parameters.get("collegeTypeLable") == null) {
				return "学院类型";
			} else if (parameters.get("collegeTypeLable").toString().equals(""))
				return null;
			else
				return parameters.get("collegeTypeLable").toString();
		}
	}

	public static String getProcessLable(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("processEnLable") == null) {
				return "Process";
			} else if (parameters.get("processEnLable").toString().equals(""))
				return null;
			else
				return parameters.get("processEnLable").toString();
		} else {
			if (parameters == null || parameters.get("processLable") == null) {
				return "进程";
			} else if (parameters.get("processLable").toString().equals(""))
				return null;
			else
				return parameters.get("processLable").toString();
		}
	}
	
	public static String getCollegeLable(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("collegeEnLable") == null) {
				return "College";
			} else if (parameters.get("collegeEnLable").toString().equals(""))
				return null;
			else
				return parameters.get("collegeEnLable").toString();
		} else {
			if (parameters == null || parameters.get("collegeLable") == null) {
				return "学院";
			} else if (parameters.get("collegeLable").toString().equals(""))
				return null;
			else
				return parameters.get("collegeLable").toString();
		}
	}
	public static String getCollege1Lable(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("college1EnLable") == null) {
				return "College1";
			} else if (parameters.get("college1EnLable").toString().equals(""))
				return null;
			else
				return parameters.get("college1EnLable").toString();
		} else {
			if (parameters == null || parameters.get("college1Lable") == null) {
				return "二级单位";
			} else if (parameters.get("college1Lable").toString().equals(""))
				return null;
			else
				return parameters.get("college1Lable").toString();
		}
	}
	public static String getCollege2Lable(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("college2EnLable") == null) {
				return "College2";
			} else if (parameters.get("college2EnLable").toString().equals(""))
				return null;
			else
				return parameters.get("college2EnLable").toString();
		} else {
			if (parameters == null || parameters.get("college2Lable") == null) {
				return "三级单位";
			} else if (parameters.get("college2Lable").toString().equals(""))
				return null;
			else
				return parameters.get("college2Lable").toString();
		}
	}

	public static String getSecondTypeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("secondTypeEnLabel") == null) {
				return "Type";
			} else if (parameters.get("secondEnTypeLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("secondEnTypeLabel").toString();
		} else {
			if (parameters == null || parameters.get("secondTypeLabel") == null) {
				return "类型";
			} else if (parameters.get("secondTypeLabel").toString().equals(""))
				return null;
			else
				return parameters.get("secondTypeLabel").toString();
		}
	}

	public static String getMajorLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("majorEnLabel") == null) {
				return "Major";
			} else if (parameters.get("majorEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("majorEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("majorLabel") == null) {
				return "专业";
			} else if (parameters.get("majorLabel").toString().equals(""))
				return null;
			else
				return parameters.get("majorLabel").toString();
		}
	}

	public static String getGradeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("gradeEnLabel") == null) {
				return "Grade";
			} else if (parameters.get("gradeEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("gradeEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("gradeLabel") == null) {
				return "年级";
			} else if (parameters.get("gradeLabel").toString().equals(""))
				return null;
			else
				return parameters.get("gradeLabel").toString();
		}
	}
	public static String getGrade1Label(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("grade1EnLabel") == null) {
				return "Grade";
			} else if (parameters.get("grade1EnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("grade1EnLabel").toString();
		} else {
			if (parameters == null || parameters.get("grade1Label") == null) {
				return "年级";
			} else if (parameters.get("grade1Label").toString().equals(""))
				return null;
			else
				return parameters.get("grade1Label").toString();
		}
	}

	public static String getClassLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("classEnLabel") == null) {
				return "Class";
			} else if (parameters.get("classEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("classLabel").toString();
		} else {
			if (parameters == null || parameters.get("classLabel") == null) {
				return "班级";
			} else if (parameters.get("classLabel").toString().equals(""))
				return null;
			else
				return parameters.get("classLabel").toString();
		}
	}

	public static String getLoanStatusLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("loanStatusEnLabel") == null) {
				return "LoanStatus";
			} else if (parameters.get("loanStatusEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("loanStatusLabel").toString();
		} else {
			if (parameters == null || parameters.get("loanStatusLabel") == null) {
				return "审核状态";
			} else if (parameters.get("loanStatusLabel").toString().equals(""))
				return null;
			else
				return parameters.get("loanStatusLabel").toString();
		}
	}

	public static String getLoanStatusNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("loanStatusNameEnLabel") == null) {
				return "LoanStatusName";
			} else if (parameters.get("loanStatusNameEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("loanStatusNameLabel").toString();
		} else {
			if (parameters == null
					|| parameters.get("loanStatusNameLabel") == null) {
				return "贷款状态";
			} else if (parameters.get("loanStatusNameLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("loanStatusNameLabel").toString();
		}
	}

	public static String getGroupLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("groupEnLabel") == null) {
				return "Group";
			} else if (parameters.get("groupEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("groupEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("groupLabel") == null) {
				return "组";
			} else if (parameters.get("groupLabel").toString().equals(""))
				return null;
			else
				return parameters.get("groupLabel").toString();
		}
	}

	public static String getScholarTypeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("scholarTypeEnLabel") == null) {
				return "ScholarType";
			} else if (parameters.get("scholarTypeEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("scholarTypeEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("scholarTypeLabel") == null) {
				return "奖学金类型";
			} else if (parameters.get("scholarTypeLabel").toString().equals(""))
				return null;
			else
				return parameters.get("scholarTypeLabel").toString();
		}
	}
	
	public static String getTermLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("termEnLabel") == null) {
				return "Term";
			} else if (parameters.get("termEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("termEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("termLabel") == null) {
				return "学期";
			} else if (parameters.get("termLabel").toString().equals(""))
				return null;
			else
				return parameters.get("termLabel").toString();
		}
	}

	public static String getProcessStateLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("processStateEnLabel") == null) {
				return "ProcessState";
			} else if (parameters.get("processStateEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("processStateEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("processStateLabel") == null) {
				return "状态";
			} else if (parameters.get("processStateLabel").toString().equals(""))
				return null;
			else
				return parameters.get("processStateLabel").toString();
		}
	}
	
	public static String getReviewModeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("reviewModeEnLabel") == null) {
				return "ReviewMode";
			} else if (parameters.get("reviewModeEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("reviewModeEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("reviewModeLabel") == null) {
				return "评阅方式";
			} else if (parameters.get("reviewModeLabel").toString().equals(""))
				return null;
			else
				return parameters.get("reviewModeLabel").toString();
		}
	}
	
	
	public static String getQueryDateLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("queryDateEnLabel") == null) {
				return "Date";
			} else if (parameters.get("queryDateEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("queryDateEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("queryDateLabel") == null) {
				return "日期";
			} else if (parameters.get("queryDateLabel").toString().equals(""))
				return null;
			else
				return parameters.get("queryDateLabel").toString();
		}
	}
	public static String getStudyStateLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("studyStateEnLabel") == null) {
				return "StudyState";
			} else if (parameters.get("studyStateEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("studyStateEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("studyStateLabel") == null) {
				return "学籍状态";
			} else if (parameters.get("studyStateLabel").toString().equals(""))
				return null;
			else
				return parameters.get("studyStateLabel").toString();
		}
	}
	public static String getLeaveCauseLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("leaveCauseEnLabel") == null) {
				return "LeaveCause";
			} else if (parameters.get("leaveCauseEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("leaveCauseEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("leaveCauseLabel") == null) {
				return "离校情况";
			} else if (parameters.get("leaveCauseLabel").toString().equals(""))
				return null;
			else
				return parameters.get("leaveCauseLabel").toString();
		}
	}
	public static String getGraDegreeDateLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("graDegreeDateEnLabel") == null) {
				return "graDegreeDate";
			} else if (parameters.get("graDegreeDateEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("graDegreeDateEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("graDegreeDateLabel") == null) {
				return "授学位日期";
			} else if (parameters.get("graDegreeDateLabel").toString().equals(""))
				return null;
			else
				return parameters.get("graDegreeDateLabel").toString();
		}
	}

		
	public static String getYearLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("yearEnLabel") == null) {
				return "Year";
			} else if (parameters.get("yearEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("yearEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("yearLabel") == null) {
				return "年度";
			} else if (parameters.get("yearLabel").toString().equals(""))
				return null;
			else
				return parameters.get("yearLabel").toString();
		}
	}

	public static String getMonthLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("monthEnLabel") == null) {
				return "Month";
			} else if (parameters.get("monthEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("monthEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("monthLabel") == null) {
				return "月份";
			} else if (parameters.get("monthLabel").toString().equals(""))
				return null;
			else
				return parameters.get("monthLabel").toString();
		}
	}

	public static String getStatusLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("statusEnLabel") == null) {
				return "Status";
			} else if (parameters.get("statusEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("statusEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("statusLabel") == null) {
				return "状态";
			} else if (parameters.get("statusLabel").toString().equals(""))
				return null;
			else
				return parameters.get("statusLabel").toString();
		}
	}

	public static String getCheckStatusLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("checkStatusEnLabel") == null) {
				return "CheckStatus";
			} else if (parameters.get("checkStatusEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("checkStatusEnLabel").toString();
		} else {
			if (parameters == null
					|| parameters.get("checkStatusLabel") == null) {
				return "审核状态";
			} else if (parameters.get("checkStatusLabel").toString().equals(""))
				return null;
			else
				return parameters.get("checkStatusLabel").toString();
		}
	}

	public static String getCustomLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("customEnLabel") == null) {
				return "Custom";
			} else if (parameters.get("customEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("customEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("customLabel") == null) {
				return "自定义查询";
			} else if (parameters.get("customLabel").toString().equals(""))
				return null;
			else
				return parameters.get("customLabel").toString();
		}
	}

	public static String getDataSourceLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("dataSourceEnLabel") == null) {
				return "DataSource";
			} else if (parameters.get("dataSourceEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("dataSourceEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("dataSourceLabel") == null) {
				return "数据源";
			} else if (parameters.get("dataSourceLabel").toString().equals(""))
				return null;
			else
				return parameters.get("dataSourceLabel").toString();
		}
	}

	public static String getStartDateLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("startDateEnLabel") == null) {
				return "Start";
			} else if (parameters.get("startDateEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("startDateEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("startDateLabel") == null) {
				return "起始";
			} else if (parameters.get("startDateLabel").toString().equals(""))
				return null;
			else
				return parameters.get("startDateLabel").toString();
		}
	}

	public static String getEndDateLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("endDateEnLabel") == null) {
				return "End";
			} else if (parameters.get("endDateEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("endDateEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("endDateLabel") == null) {
				return "截止";
			} else if (parameters.get("endDateLabel").toString().equals(""))
				return null;
			else
				return parameters.get("endDateLabel").toString();
		}
	}

	public static String getPerNumLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("perNumEnLabel") == null) {
				return "StuNum";
			} else if (parameters.get("perNumEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("perNumEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("perNumLabel") == null) {
				return "学号";
			} else if (parameters.get("perNumLabel").toString().equals(""))
				return null;
			else
				return parameters.get("perNumLabel").toString();
		}
	}

	public static String getPrompt0Label(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("prompt0EnLabel") == null) {
				return "prompt";
			} else if (parameters.get("prompt0EnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("prompt0EnLabel").toString();
		} else {
			if (parameters == null || parameters.get("prompt0Label") == null) {
				return "提示";
			} else if (parameters.get("prompt0Label").toString().equals(""))
				return null;
			else
				return parameters.get("prompt0Label").toString();
		}
	}
	public static String getPrompt1Label(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("prompt1EnLabel") == null) {
				return "prompt";
			} else if (parameters.get("prompt1EnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("prompt1EnLabel").toString();
		} else {
			if (parameters == null || parameters.get("prompt1Label") == null) {
				return "提示";
			} else if (parameters.get("prompt1Label").toString().equals(""))
				return null;
			else
				return parameters.get("prompt1Label").toString();
		}
	}
	public static String getSelectLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("selectEnLabel") == null) {
				return "select";
			} else if (parameters.get("selectEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("selectEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("selectLabel") == null) {
				return "选中";
			} else if (parameters.get("selectLabel").toString().equals(""))
				return null;
			else
				return parameters.get("selectLabel").toString();
		}
	}
	
	public static String getInputNum0Label(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("inputNum0EnLabel") == null) {
				return "from";
			} else if (parameters.get("inputNum0EnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("inputNum0EnLabel").toString();
		} else {
			if (parameters == null || parameters.get("inputNum0Label") == null) {
				return "从";
			} else if (parameters.get("inputNum0Label").toString().equals(""))
				return null;
			else
				return parameters.get("inputNum0Label").toString();
		}
	}
	public static String getInputNum1Label(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("inputNum1EnLabel") == null) {
				return "to";
			} else if (parameters.get("inputNum1EnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("inputNum1EnLabel").toString();
		} else {
			if (parameters == null || parameters.get("inputNum1Label") == null) {
				return "至";
			} else if (parameters.get("inputNum1Label").toString().equals(""))
				return null;
			else
				return parameters.get("inputNum1Label").toString();
		}
	}

	
	public static String getPerNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("perNameEnLabel") == null) {
				return "Name";
			} else if (parameters.get("perNameEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("perNameEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("perNameLabel") == null) {
				return "姓名";
			} else if (parameters.get("perNameLabel").toString().equals(""))
				return null;
			else
				return parameters.get("perNameLabel").toString();
		}
	}

	public static String getCollegeNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("collegeNameEnLabel") == null) {
				return "College";
			} else if (parameters.get("collegeNameEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("collegeNameEnLabel").toString();
		} else {
			if (parameters == null
					|| parameters.get("collegeNameLabel") == null) {
				return "单位";
			} else if (parameters.get("collegeNameLabel").toString().equals(""))
				return null;
			else
				return parameters.get("collegeNameLabel").toString();
		}
	}

	public static String getPerEngNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("perEngNameEnLabel") == null) {
				return "Name";
			} else if (parameters.get("perEngNameEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("perEngNameEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("perEngNameLabel") == null) {
				return "英文名";
			} else if (parameters.get("perEngNameLabel").toString().equals(""))
				return null;
			else
				return parameters.get("perEngNameLabel").toString();
		}
	}
	public static String getPerEnglishFamilyNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("perEnglishFamilyNameEnLabel") == null) {
				return "Name";
			} else if (parameters.get("perEnglishFamilyNameEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("perEnglishFamilyNameEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("perEnglishFamilyNameLabel") == null) {
				return "英文姓";
			} else if (parameters.get("perEnglishFamilyNameLabel").toString().equals(""))
				return null;
			else
				return parameters.get("perEnglishFamilyNameLabel").toString();
		}
	}
	public static String getPerEnglishGivenNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("perEnglishGivenNameEnLabel") == null) {
				return "Name";
			} else if (parameters.get("perEnglishGivenNameEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("perEnglishGivenNameEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("perEnglishGivenNameLabel") == null) {
				return "英文名";
			} else if (parameters.get("perEnglishGivenNameLabel").toString().equals(""))
				return null;
			else
				return parameters.get("perEnglishGivenNameLabel").toString();
		}
	}

	public static String getPerIdCardLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("perIdCardEnLabel") == null) {
				return "IdNo";
			} else if (parameters.get("perIdCardEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("perIdCardEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("perIdCardLabel") == null) {
				return "证件号码";
			} else if (parameters.get("perIdCardLabel").toString().equals(""))
				return null;
			else
				return parameters.get("perIdCardLabel").toString();
		}
	}

	public static String getMobilePhoneLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("mobilePhoneEnLabel") == null) {
				return "Mobile";
			} else if (parameters.get("mobilePhoneEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("mobilePhoneEnLabel").toString();
		} else {
			if (parameters == null
					|| parameters.get("mobilePhoneLabel") == null) {
				return "手机";
			} else if (parameters.get("mobilePhoneLabel").toString().equals(""))
				return null;
			else
				return parameters.get("mobilePhoneLabel").toString();
		}
	}

	public static String getExamNoLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("courseNumEnLabel") == null) {
				return "ExamNo";
			} else if (parameters.get("examNoEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("examNoEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("examNoLabel") == null) {
				return "准考证号";
			} else if (parameters.get("examNoLabel").toString().equals(""))
				return null;
			else
				return parameters.get("examNoLabel").toString();
		}
	}
	public static String getCourseNumLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("courseNumEnLabel") == null) {
				return "CourseNum";
			} else if (parameters.get("courseNumEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("courseNumEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("courseNumLabel") == null) {
				return "课程号";
			} else if (parameters.get("courseNumLabel").toString().equals(""))
				return null;
			else
				return parameters.get("courseNumLabel").toString();
		}
	}

	public static String getCourseNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("courseNameEnLabel") == null) {
				return "Course Name";
			} else if (parameters.get("courseNameEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("courseNameEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("courseNameLabel") == null) {
				return "课程名";
			} else if (parameters.get("courseNameLabel").toString().equals(""))
				return null;
			else
				return parameters.get("courseNameLabel").toString();
		}
	}

	public static String getCourseNoLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("courseNoEnLabel") == null) {
				return "CourseNo";
			} else if (parameters.get("courseNoEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("courseNoEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("courseNoLabel") == null) {
				return "课序号";
			} else if (parameters.get("courseNoNoLabel").toString().equals(""))
				return null;
			else
				return parameters.get("courseNoLabel").toString();
		}
	}

	public static String getTeachTaskTypeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("teachTaskTypeEnLabel") == null) {
				return "Task Type";
			} else if (parameters.get("teachTaskTypeEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("teachTaskTypeEnLabel").toString();
		} else {
			if (parameters == null
					|| parameters.get("teachTaskTypeLabel") == null) {
				return "任务类型";
			} else if (parameters.get("teachTaskTypeLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("teachTaskTypeLabel").toString();
		}
	}
	public static String getButton0NameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("button0EnLabel") == null) {
				return "Button0";
			} else if (parameters.get("button0EnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("button0EnLabel").toString();
		} else {
			if (parameters == null || parameters.get("button0Label") == null) {
				return "按钮0";
			} else if (parameters.get("button0Label").toString().equals(""))
				return null;
			else
				return parameters.get("button0Label").toString();
		}
	}
	public static String getButton1NameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("button1EnLabel") == null) {
				return "Button1";
			} else if (parameters.get("button1EnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("button1EnLabel").toString();
		} else {
			if (parameters == null || parameters.get("button1Label") == null) {
				return "按钮1";
			} else if (parameters.get("button1Label").toString().equals(""))
				return null;
			else
				return parameters.get("button1Label").toString();
		}
	}
	public static String getButton2NameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("button2EnLabel") == null) {
				return "Button2";
			} else if (parameters.get("button2EnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("button2EnLabel").toString();
		} else {
			if (parameters == null || parameters.get("button2Label") == null) {
				return "按钮2";
			} else if (parameters.get("button2Label").toString().equals(""))
				return null;
			else
				return parameters.get("button2Label").toString();
		}
	}

	
	public static String getQueryNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("queryEnLabel") == null) {
				return "Query";
			} else if (parameters.get("queryEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("queryEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("queryLabel") == null) {
				return "查询";
			} else if (parameters.get("queryLabel").toString().equals(""))
				return null;
			else
				return parameters.get("queryLabel").toString();
		}
	}

	public static String getComputeNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("computeEnLabel") == null) {
				return "Compute";
			} else if (parameters.get("computeEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("computeEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("computeLabel") == null) {
				return "统计";
			} else if (parameters.get("computeLabel").toString().equals(""))
				return null;
			else
				return parameters.get("computeLabel").toString();
		}
	}

	public static String getClearNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("clearEnLabel") == null) {
				return "Clear";
			} else if (parameters.get("clearEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("clearEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("clearLabel") == null) {
				return "清除";
			} else if (parameters.get("clearLabel").toString().equals(""))
				return null;
			else
				return parameters.get("clearLabel").toString();
		}
	}

	public static String getExportNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("exportEnLabel") == null) {
				return "Export";
			} else if (parameters.get("exportEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("exportEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("exportLabel") == null) {
				return "导出";
			} else if (parameters.get("exportLabel").toString().equals(""))
				return null;
			else
				return parameters.get("exportLabel").toString();
		}
	}
	public static String getImportNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("importEnLabel") == null) {
				return "Import";
			} else if (parameters.get("importEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("importEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("importLabel") == null) {
				return "导入";
			} else if (parameters.get("importLabel").toString().equals(""))
				return null;
			else
				return parameters.get("importLabel").toString();
		}
	}

	public static String getBuildingLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("buildingEnLabel") == null) {
				return "Buiding";
			} else if (parameters.get("buildingEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("buildingEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("buildingLabel") == null) {
				return "楼";
			} else if (parameters.get("buildingLabel").toString().equals(""))
				return null;
			else
				return parameters.get("buildingLabel").toString();
		}
	}

	public static String getBuildingTypeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("buildingTypeEnLabel") == null) {
				return "BuidingType";
			} else if (parameters.get("buildingTypeEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("buildingTypeEnLabel").toString();
		} else {
			if (parameters == null
					|| parameters.get("buildingTypeLabel") == null) {
				return "类型";
			} else if (parameters.get("buildingTypeLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("buildingTypeLabel").toString();
		}
	}

	public static String getFloorLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("floorEnLabel") == null) {
				return "Floor";
			} else if (parameters.get("floorEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("floorEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("floorLabel") == null) {
				return "层";
			} else if (parameters.get("floorLabel").toString().equals(""))
				return null;
			else
				return parameters.get("floorLabel").toString();
		}
	}

	public static String getlocationCodeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("locationCodeEnLabel") == null) {
				return "RoomNum";
			} else if (parameters.get("locationCodeEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("locationCodeEnLabel").toString();
		} else {
			if (parameters == null
					|| parameters.get("locationCodeLabel") == null) {
				return "房间编号";
			} else if (parameters.get("locationCodeLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("locationCodeLabel").toString();
		}
	}

	public static String getLeftCapacityLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("leftCapacityEnLabel") == null) {
				return "LeftCapacity";
			} else if (parameters.get("leftCapacityEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("leftCapacityLabel").toString();

		} else {
			if (parameters == null
					|| parameters.get("leftCapacityLabel") == null) {
				return "剩余量";
			} else if (parameters.get("leftCapacityLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("leftCapacityLabel").toString();
		}
	}

	public static String getRoomNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("roomNameEnLabel") == null) {
				return "Room Name";
			} else if (parameters.get("roomNameEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("roomNameEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("roomNameLabel") == null) {
				return "房间名";
			} else if (parameters.get("roomNameLabel").toString().equals(""))
				return null;
			else
				return parameters.get("roomNameLabel").toString();
		}
	}

	public static String getRoomTypeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("roomTypeEnLabel") == null) {
				return "Room Type";
			} else if (parameters.get("roomTypeEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("roomTypeEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("roomTypeLabel") == null) {
				return "房间类型";
			} else if (parameters.get("roomTypeLabel").toString().equals(""))
				return null;
			else
				return parameters.get("roomTypeLabel").toString();
		}
	}

	public static String getRoomSecondTypeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("roomSecondTypeEnLabel") == null) {
				return "Class";
			} else if (parameters.get("roomSecondTypeEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("roomSecondTypeEnLabel").toString();
		} else {
			if (parameters == null
					|| parameters.get("roomSecondTypeLabel") == null) {
				return "分类";
			} else if (parameters.get("roomSecondTypeLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("roomSecondTypeLabel").toString();
		}
	}

	public static String getRoomStatusLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("roomStatusTypeEnLabel") == null) {
				return "Status";
			} else if (parameters.get("roomStatusEnLabel").toString()
					.equals(""))
				return null;
			else
				return parameters.get("roomStatusEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("roomStatusLabel") == null) {
				return "状态";
			} else if (parameters.get("roomStatusLabel").toString().equals(""))
				return null;
			else
				return parameters.get("roomStatusLabel").toString();
		}
	}
	public static String getButton0NameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("button0Cmd") == null) {
			return "button0";
		} else if (parameters.get("button0Cmd").toString().equals(""))
			return null;
		else
			return parameters.get("button0Cmd").toString();
	}
	public static String getButton1NameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("button1Cmd") == null) {
			return "button1";
		} else if (parameters.get("button1Cmd").toString().equals(""))
			return null;
		else
			return parameters.get("button1Cmd").toString();
	}
	public static String getButton2NameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("button2Cmd") == null) {
			return "button2";
		} else if (parameters.get("button2Cmd").toString().equals(""))
			return null;
		else
			return parameters.get("button2Cmd").toString();
	}

	public static String getQueryNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("queryCmd") == null) {
			return "query";
		} else if (parameters.get("queryCmd").toString().equals(""))
			return null;
		else
			return parameters.get("queryCmd").toString();
	}

	public static String getComputeNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("computeCmd") == null)
			return "compute";
		else
			return parameters.get("computeCmd").toString();
	}

	public static String getClearNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("clearCmd") == null)
			return "clear";
		else
			return parameters.get("clearCmd").toString();
	}

	public static String getExportNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("exportCmd") == null)
			return "export";
		else
			return parameters.get("exportCmd").toString();
	}
	public static String getImportNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("importCmd") == null)
			return "import";
		else
			return parameters.get("importCmd").toString();
	}

	// ////////////////////////////////////

	public static String[] ListToArray(List<String> list) {
		if (list == null || list.size() == 0)
			return null;
		String ret[] = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i);
		}
		return ret;
	}

	public static String getCollegeType(HashMap parameters) {
		if (parameters == null) {
			return CommonComponentConstants.DEFAULT_COLLEGE_TYPE;
		}
		if (parameters.get("collegeType") != null)
			return (String) parameters.get("collegeType");
		else if (UFactory.getModelSession().getEnvironmentProperties(
				"collegeType") != null)
			return UFactory.getModelSession().getEnvironmentProperties(
					"collegeType");
		else
			return CommonComponentConstants.DEFAULT_COLLEGE_TYPE;
	}
	public static String getCollegeTypeDict(HashMap parameters) {
		if (parameters == null) {
			return CommonComponentConstants.COLLEGE_TYPE_DICTIONARY_NORMAL;
		}
		if (parameters.get("collegeTypeDict") != null)
			return (String) parameters.get("collegeTypeDict");
		else
			return CommonComponentConstants.COLLEGE_TYPE_DICTIONARY_NORMAL;
	}

	public static String getStuTypeCollegeKey(HashMap parameters) {
		if (parameters != null)
			return (String) parameters.get("stuTypeCollegeKey");
		else
			return null;
	}

	public static String getStuTypeMajorKey(HashMap parameters) {
		if (parameters != null)
			return (String) parameters.get("stuTypeMajorKey");
		else
			return null;
	}

	public static String[] getCollegeMapKey(HashMap parameters) {
		if (parameters == null) {
			return null;
		}
		if (parameters.get("collegeMapKey") == null)
			return null;
		String str = (String) parameters.get("collegeMapKey");
		StringTokenizer sz = new StringTokenizer(str, "|");
		String a[] = new String[sz.countTokens()];
		for (int i = 0; i < a.length; i++) {
			a[i] = sz.nextToken();
		}
		return a;
	}

	public static String getMajorType(HashMap parameters) {
		if (parameters == null) {
			return CommonComponentConstants.DEFAULT_MAJOR_TYPE;
		}
		if (parameters.get("majorType") != null)
			return (String) parameters.get("majorType");
		else if (UFactory.getModelSession().getEnvironmentProperties(
				"majorType") != null)
			return (String) parameters.get("majorType");
		else
			return CommonComponentConstants.DEFAULT_MAJOR_TYPE;
	}

	public static String getStatusType(HashMap parameters) {
		return null;
	}

	public static String getPerTypeCode(HashMap parameters) {
		if (parameters == null) {
			return "1";
		}
		if (parameters.get("perTypeCode") == null)
			return "1";
		else
			return parameters.get("perTypeCode").toString();

	}

	public static String getBType(HashMap parameters) {
		String bType = CommonComponentConstants.DEFAULT_COLLEGE_BUSINESS_TYPE;
		if (parameters == null) {
			return bType;
		}
		if (parameters.get("bType") == null)
			return bType;
		else
			return parameters.get("bType").toString();
	}

	
	public static String getStatusDictionary(HashMap parameters) {
		if (parameters == null) {
			return null;
		}
		return (String) parameters.get("statusDictionary");
	}

	public static boolean isExistComName(HashMap parameters, String comName) {
		if (parameters == null)
			return false;
		String str = (String) parameters.get("visibleItems");
		if (str == null || str.length() == 0)
			return false;
		int index = str.indexOf(comName);
		if (index < 0)
			return false;
		else
			return true;

	}

	public static List<String> getVissibleNameList(HashMap parameters) {
		if (parameters == null) {
			return getDefaultVisibleNameList();
		}
		String str = (String) parameters.get("visibleItems");
		if (str != null && str.length() != 0) {
			return getVisibleNameListByVisbelItems(str);
		}
		str = (String) parameters.get("visbleType");
		if (str != null && str.length() != 0) {
			return getVisibleNameListByVisbelType(str);
		}
		return null;
	}

	public static List<ListOptionInfo> getOptionListFromString(String value) {
		if (value == null)
			return null;
		StringTokenizer sz = new StringTokenizer(value, ")");
		List<ListOptionInfo> retList = new ArrayList<ListOptionInfo>();
		String str, as;
		int index;
		while (sz.hasMoreTokens()) {
			as = sz.nextToken();
			str = as.substring(1, as.length());
			index = str.indexOf("_");
			if (index > 0)
				retList.add(new ListOptionInfo(str.substring(0, index), str
						.substring(index + 1, str.length())));
		}
		return retList;
	}

	public static HashMap<String, ExtendItemObject> getExtendItemMap(
			String parStr) {
		HashMap<String, ExtendItemObject> map = new HashMap<String, ExtendItemObject>();
		StringTokenizer sz = new StringTokenizer(parStr, "/");
		String tempStr, str;
		StringTokenizer tsz;
		int count;
		String sW;
		String name, value;
		int index;
		while (sz.hasMoreTokens()) {
			tempStr = sz.nextToken();
			tsz = new StringTokenizer(tempStr, ",");
			if (tsz.countTokens() == 0)
				continue;
			ExtendItemObject o = new ExtendItemObject();
			o.addSelectItem = false;
			sW = null;
			while (tsz.hasMoreTokens()) {
				str = tsz.nextToken();
				index = str.indexOf("-");
				if (index < 0)
					continue;
				name = str.substring(0, index);
				value = str.substring(index + 1, str.length());
				if (name.equals("name"))
					o.name = value;
				else if (name.equals("label"))
					o.label = value;
				else if (name.equals("enLabel"))
					o.enLabel = value;
				else if (name.equals("member"))
					o.member = value;
				else if (name.equals("members"))
					o.member = value;
				else if (name.equals("width")) {
					o.width = Integer.parseInt(value);
				} else if (name.equals("dictionary")) {
					o.dictionary = value;
				} else if (name.equals("filter")) {
					o.filter = value;
				} else if (name.equals("doCmd")) {
					o.doCmd = value;
				} else if (name.equals("option")) {
					o.optionList = getOptionListFromString(value);
				} else if (name.equals("type")) {
					o.type = value;
				} else if (name.equals("addSelectItem")) {
					if (value.equals("true"))
						o.addSelectItem = true;
				}
			}
			if (o.member == null)
				o.member = o.name;
			if (o.members == null)
				o.members = o.name + "s";
			map.put(o.name, o);
		}
		return map;
	}

	public static List<String> getDefaultVisibleNameList() {
		List visibleNameList = new ArrayList<String>();
		visibleNameList.add("area");
		visibleNameList.add("campus");
		visibleNameList.add("college");
		visibleNameList.add("stuTypeLevel");
		visibleNameList.add("stuTypeCode");
		visibleNameList.add("teaTypeCode");
		visibleNameList.add("major");
		visibleNameList.add("grade");
		visibleNameList.add("class");
		visibleNameList.add("group");
		visibleNameList.add("term");
		visibleNameList.add("studyState");
		visibleNameList.add("leaveCause");
		visibleNameList.add("status");
		visibleNameList.add("perNum");
		visibleNameList.add("perName");
		visibleNameList.add("query");
		visibleNameList.add("conditionQuery");
		visibleNameList.add("export");
		return visibleNameList;
	}

	public static List<String> getVisibleNameListByVisbelType(String str) {

		List<String> visibleNameList = new ArrayList<String>();
		if (str.equals("grad-stu")) {
			visibleNameList.add("college");
			visibleNameList.add("stuTypeLevel");
			visibleNameList.add("major");
			visibleNameList.add("grade");
			visibleNameList.add("query");
			visibleNameList.add("conditionQuery");
		} else if (str.equals("bkjwxt-stu")) {
			visibleNameList.add("college");
			visibleNameList.add("major");
			visibleNameList.add("grade");
			visibleNameList.add("class");
			visibleNameList.add("query");
			visibleNameList.add("conditionQuery");
		} else if (str.equals("sc-stu")) {
			visibleNameList.add("college");
			visibleNameList.add("stuTypeLevel");
			visibleNameList.add("major");
			visibleNameList.add("grade");
			visibleNameList.add("class");
			visibleNameList.add("query");
			visibleNameList.add("conditionQuery");
		}
		return visibleNameList;
	}

	public static List<String> getVisibleNameListByVisbelItems(String visString) {
		List visibleNameList = new ArrayList<String>();
		StringTokenizer sz = new StringTokenizer(visString, "/");
		visibleNameList = new ArrayList<String>();
		while (sz.hasMoreTokens()) {
			visibleNameList.add(sz.nextToken());
		}
		return visibleNameList;
	}

	public static List<ExtendItemObject> extendMapToList(
			HashMap<String, ExtendItemObject> map) {
		if (map == null)
			return null;
		List<ExtendItemObject> list = new ArrayList<ExtendItemObject>();
		Iterator it = map.keySet().iterator();
		String key;
		while (it.hasNext()) {
			key = (String) it.next();
			list.add(map.get(key));
		}
		return list;
	}

	public static HashMap<String, ExtendItemObject> getExtendNameMap(
			HashMap parameters, String key) {
		if (parameters == null) {
			return null;
		}
		String str = (String) parameters.get(key);
		if (str != null && str.length() != 0) {
			return getExtendItemMap(str);
		}
		return null;
	}

	public static List getGradeList() {
		List list = new ArrayList();
		int currentYear = DateTimeTool.getYear();
		for (int year = currentYear + 1; year >= currentYear - 10; year--) {
			list.add(new ListOptionInfo("" + year, "" + year));
		}
		return list;
	}

	public static String getBaseClassBeanName(HashMap parameters) {
		if (parameters == null)
			return null;
		String names = (String) parameters.get("beanName");
		if (names == null || names.length() == 0)
			return null;
		StringTokenizer sz = new StringTokenizer(names, "/");
		String par, key;
		int index;
		while (sz.hasMoreTokens()) {
			par = sz.nextToken();
			index = par.indexOf('-');
			if (index > 0) {
				key = par.substring(0, index);
				if (key.equals("class"))
					return par.substring(index + 1, par.length());
			}
		}
		return null;
	}

	public static String getBaseCollegeBeanName(HashMap parameters) {
		if (parameters == null)
			return null;
		String names = (String) parameters.get("beanName");
		if (names == null || names.length() == 0)
			return null;
		StringTokenizer sz = new StringTokenizer(names, "/");
		String par, key;
		int index;
		while (sz.hasMoreTokens()) {
			par = sz.nextToken();
			index = par.indexOf('-');
			if (index > 0) {
				key = par.substring(0, index);
				if (key.equals("college"))
					return par.substring(index + 1, par.length());
			}
		}
		return null;
	}

	public static String getStatusBeanName(HashMap parameters) {
		if (parameters == null)
			return null;
		String names = (String) parameters.get("beanName");
		if (names == null || names.length() == 0)
			return null;
		StringTokenizer sz = new StringTokenizer(names, "/");
		String par, key;
		int index;
		while (sz.hasMoreTokens()) {
			par = sz.nextToken();
			index = par.indexOf('-');
			if (index > 0) {
				key = par.substring(0, index);
				if (key.equals("status"))
					return par.substring(index + 1, par.length());
			}
		}
		return null;
	}

	public static String getCourseBeanName(HashMap parameters) {
		if (parameters == null)
			return null;
		String names = (String) parameters.get("beanName");
		if (names == null || names.length() == 0)
			return null;
		StringTokenizer sz = new StringTokenizer(names, "/");
		String par, key;
		int index;
		while (sz.hasMoreTokens()) {
			par = sz.nextToken();
			index = par.indexOf('-');
			if (index > 0) {
				key = par.substring(0, index);
				if (key.equals("couse"))
					return par.substring(index + 1, par.length());
			}
		}
		return null;
	}

	public static String changeCodeListToSQlString(List<String> codeList) {
		if (codeList == null || codeList.size() == 0)
			return null;
		int i;
		String value, code;
		StringTokenizer sz;
		String retString = "(";
		for (i = 0; i < codeList.size(); i++) {
			Object o = codeList.get(i);
			if (o instanceof ListOptionInfo)
				value = ((ListOptionInfo) o).getValue();
			else
				value = o.toString();
			if (value.equalsIgnoreCase("all")) {
				return null;
			} else {
				sz = new StringTokenizer(value, ",");
				while (sz.hasMoreTokens()) {
					code = sz.nextToken();
					retString += "'" + code + "',";
				}
			}
		}
		if (retString.length() > 1)
			return retString.substring(0, retString.length() - 1) + ")";
		else
			return null;

	}

	public static String[] changeCodeListToArrays(List codeList) {
		if (codeList == null || codeList.size() == 0)
			return null;
		int i;
		String value;
		StringTokenizer sz;
		List<String> tempList = new ArrayList<String>();
		for (i = 0; i < codeList.size(); i++) {
			Object o = codeList.get(i);
			if (o instanceof ListOptionInfo)
				value = ((ListOptionInfo) o).getValue();
			else
				value = o.toString();
			if (value.equalsIgnoreCase("all")) {
				return null;
			} else {
				sz = new StringTokenizer(value, ",");
				while (sz.hasMoreTokens()) {
					tempList.add(sz.nextToken());
				}
			}
		}
		String[] codes = new String[tempList.size()];
		for (i = 0; i < tempList.size(); i++) {
			codes[i] = tempList.get(i);
		}
		return codes;
	}

	public static Integer changeStringToInteger(String str) {
		if (str == null || str.length() == 0)
			return null;
		else
			return new Integer(str);
	}

	public static Integer[] changeStringsToIntegers(String strs[]) {
		if (strs == null || strs.length == 0)
			return null;
		else {
			Integer is[] = new Integer[strs.length];
			{
				for (int i = 0; i < strs.length; i++) {
					if (strs[i] == null || strs[i].length() == 0)
						is[i] = null;
					else
						is[i] = new Integer(strs[i]);
				}
			}
			return is;
		}
	}

	public static boolean getAddrIsVisible(HashMap parameters) {
		if (parameters == null)
			return false;
		if (parameters.get("addrVisible") != null
				&& parameters.get("addrVisible").toString().equals("true")) {
			return true;
		} else
			return false;
	}

	public static String getBuildingType(HashMap parameters) {
		if (parameters == null)
			return null;
		if (parameters.get("buildingType") != null) {
			return (String) parameters.get("buildingType");
		} else
			return null;
	}

	public static String getRoomType(HashMap parameters) {
		if (parameters == null)
			return null;
		if (parameters.get("roomType") != null) {
			return (String) parameters.get("roomType");
		} else
			return null;
	}

	public static String getSystemMark(HashMap parameters) {
		if (parameters == null)
			return "1";
		if (parameters.get("systemMark") != null) {
			return (String) parameters.get("systemMark");
		} else
			return "1";
	}

	public static Boolean isCollegeFilterMark(HashMap parameters) {
		if (parameters == null)
			return true;
		if (parameters.get("collegeFilterMark") != null) {
			return new Boolean((String) parameters.get("collegeFilterMark"));
		} else
			return true;
	}

	public static String getFloorListType(HashMap parameters) {
		if (parameters == null)
			return null;
		if (parameters.get("floorListType") != null) {
			return (String) parameters.get("floorListType");
		} else
			return null;
	}

	public static List getYearList() {
		List yearList = new ArrayList();
		int currentYear = DateTimeTool.getYear();
		for (int year = currentYear; year >= 2005; year--) {
			yearList.add(new ListOptionInfo("" + year, "" + year));
		}
		return yearList;
	}

	public static List getYearListTowMonth() {
		List yearList = new ArrayList();
		int currentYear = DateTimeTool.getYear();
		for (int year = currentYear; year >= 2005; year--) {
			yearList.add(new ListOptionInfo(year + "-06", year + "-06"));
			yearList.add(new ListOptionInfo(year + "-12", year + "-12"));
		}
		return yearList;
	}

	public static List getMonthList() {
		List monthList = new ArrayList();
		for (int month = 1; month <= 12; month++) {
			monthList.add(new ListOptionInfo("" + month, "" + month));
		}
		return monthList;
	}

	public static List<ListOptionInfo> getExpendDataSource(HashMap parameters) {
		if (parameters == null)
			return null;
		List retList = null;
		String value;
		value = (String) parameters.get("xls");
		if (value != null) {
			if (retList == null)
				retList = new ArrayList<ListOptionInfo>();
			retList.add(new ListOptionInfo("xls", "xls数据表"));
		}
		value = (String) parameters.get("dbf");
		if (value != null) {
			if (retList == null)
				retList = new ArrayList<ListOptionInfo>();
			retList.add(new ListOptionInfo("dbf", "dbf数据库"));
		}
		return retList;
	}

	public static String getTeachTaskType(HashMap parameters) {
		String type = "1";
		if (parameters == null) {
			return type;
		}
		if (parameters.get("teachTaskType") == null)
			return type;
		else
			return parameters.get("teachTaskType").toString();
	}

	public static void setDefaultFormData(HashMap parameters,
			CommonQueryForm dataForm) {
		if (parameters == null)
			return;
		String str;
		str = (String) parameters.get("area");
		if (str != null && !str.equals("")) {
			dataForm.setAreaNum(str);
		}
		str = (String) parameters.get("campus");
		if (str != null && !str.equals("")) {
			dataForm.setCampusNum(str);
		}
		str = (String) parameters.get("year");
		if (str != null && !str.equals("")) {
			dataForm.setYear(str);
		}
		str = (String) parameters.get("month");
		if (str != null && !str.equals("")) {
			dataForm.setMonth(str);
		}
		str = (String) parameters.get("status");
		if (str != null && !str.equals("")) {
			dataForm.setStatus(str);
		}
		str = (String) parameters.get("bType");
		if (str != null && !str.equals("")) {
			dataForm.setBType(str);
		}
		str = (String) parameters.get("startDate");
		if (str != null && !str.equals("")) {
			dataForm.setStartDate(DateTimeTool.getDateByString(str));
		}
		str = (String) parameters.get("endDate");
		if (str != null && !str.equals("")) {
			dataForm.setEndDate(DateTimeTool.getDateByString(str));
		}
		dataForm.setCollegeType(getCollegeType(parameters));
		dataForm.setCollegeMapKey(getCollegeMapKey(parameters));
		dataForm.setStuTypeCollegeKey(getStuTypeCollegeKey(parameters));
		dataForm.setStuTypeMajorKey(getStuTypeMajorKey(parameters));
	}

	public static void setDefaultBaseDataFormData(HashMap parameters,
			CommonBaseDataQueryForm f) {
		if (parameters == null)
			return;
		String str;
		str = (String) parameters.get("topicTitle");
		if (str != null && !str.equals("")) {
			f.setTopicTitle(str);
		}
		str = (String) parameters.get("stuTypeCode");
		if (str != null && !str.equals("")) {
			f.setStuTypeCode(str);
		}
		str = (String) parameters.get("stuTypeLevel1");
		if (str != null && !str.equals("")) {
			f.setStuTypeLevel1(str);
		}
		str = (String) parameters.get("stuTypeLevel2");
		if (str != null && !str.equals("")) {
			f.setStuTypeLevel2(str);
		}
		str = (String) parameters.get("stuTypeLevel3");
		if (str != null && !str.equals("")) {
			f.setStuTypeLevel3(str);
		}
		str = (String) parameters.get("grade");
		if (str != null && !str.equals("")) {
			f.setGrade(str);
		}
		str = (String) parameters.get("teaTypeCode");
		if (str != null && !str.equals("")) {
			f.setTeaTypeCode(str);
		}
		str = (String) parameters.get("perTypeCode");
		if (str != null && !str.equals("")) {
			f.setPerTypeCode(str);
		}
		str = (String) parameters.get("perNum");
		if (str != null && !str.equals("")) {
			f.setPerNum(str);
		}
		str = (String) parameters.get("perName");
		if (str != null && !str.equals("")) {
			f.setPerName(str);
		}
		str = (String) parameters.get("courseNum");
		if (str != null && !str.equals("")) {
			f.setCourseNum(str);
		}
		str = (String) parameters.get("courseName");
		if (str != null && !str.equals("")) {
			f.setCourseName(str);
		}
		str = (String) parameters.get("courseNo");
		if (str != null && !str.equals("")) {
			f.setCourseNo(str);
		}
		str = (String) parameters.get("teachTaskType");
		if (str != null && !str.equals("")) {
			f.setTeachTaskType(str);
		}
		str = (String) parameters.get("secondTypeCode");
		if (str != null && !str.equals("")) {
			f.setSecondTypeCode(str);
		}
		str = (String) parameters.get("statusType");
		if (str != null && !str.equals("")) {
			f.setStatusType(str);
		}
		str = (String) parameters.get("statusString");
		if (str != null && !str.equals("")) {
			f.setStatusString(str);
		}
	}

	public static void setDefaultRoomFormData(HashMap parameters,
			CommonRoomQueryForm f) {
		if (parameters == null)
			return;
		String str;
		str = (String) parameters.get("locationCode");
		if (str != null && !str.equals("")) {
			f.setLocationCode(str);
		}
		str = (String) parameters.get("roomName");
		if (str != null && !str.equals("")) {
			f.setRoomName(str);
		}
		str = (String) parameters.get("roomType");
		if (str != null && !str.equals("")) {
			f.setRoomType(str);
		}
		str = (String) parameters.get("roomSecondType");
		if (str != null && !str.equals("")) {
			f.setRoomSecondType(str);
		}
		str = (String) parameters.get("floorListType");
		if (str != null && !str.equals("")) {
			f.setFloorListType(str);
		}
	}

	public static String getSelectItemOfComboBox(UComboBoxI box) {
		if (box == null || box.getMultiple())
			return null;
		ListOptionInfo info = (ListOptionInfo) box.getSelectedItem();
		if (info == null)
			return null;
		else
			return info.getValue();
	}

	public static void setSelectItemOfComboBox(UComboBoxI box, Object str) {
		if (box == null || str == null || box.getMultiple())
			return;
		int count = box.getItemCount();
		if (count == 0)
			return;
		ListOptionInfo info;
		for (int i = 0; i < count; i++) {
			info = (ListOptionInfo) box.getItemAt(i);
			if (info.getValue().equals(str.toString())) {
				box.setSelectedIndex(i);
				return;
			}
		}
	}

	public static String[] getSelectItemsOfComboBox(UComboBoxI box) {
		if (box == null || !box.getMultiple())
			return null;
		else
			return (String[]) box.getData();
	}

	public static void setSelectItemsOfComboBox(UComboBoxI box, Object str) {
		if (box == null || str == null && !box.getMultiple())
			return;
		box.setData(str);
	}




	public static List getAddedCodeList(String name, HashMap parameters) {
		if (parameters == null)
			return null;
		String value = (String) parameters.get(name);
		if (value == null)
			return null;
		StringTokenizer sz = new StringTokenizer(value, "/");
		String str;
		int index;
		List list = new ArrayList();
		while (sz.hasMoreTokens()) {
			str = sz.nextToken();
			index = str.indexOf("-");
			if (index > 0)
				list.add(new ListOptionInfo(str.substring(0, index), str
						.substring(index + 1, str.length())));
		}
		if (list.size() == 0)
			return null;
		else
			return list;
	}
	public static String getCurrentGrade() {
		return DateTimeTool.getCurDate().substring(0,4);
	}
	public static boolean collegeIsMap(String collegeType){
		if(collegeType != null && (collegeType.equals("02") || collegeType.equals("03"))) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean getIsTermPre(HashMap parameters) {
		if (parameters == null) {
			return  false;
		}
		if (parameters.get("isTermPre") != null && parameters.get("isTermPre").equals("true"))
			return true;
		else
			return false;
	}
	
}
