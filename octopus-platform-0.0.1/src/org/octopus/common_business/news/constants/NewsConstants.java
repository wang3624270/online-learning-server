package org.octopus.common_business.news.constants;

public class NewsConstants {
	//是否已经审核
		public static final String CHECK_PASS="1";
		public static final String NOT_CHECK_PASS="0";
		public static final String NEWSIPPORT ="114.215.99.2:8880";
		
		private static final int pageSize = 10;
		
		private static final int listPageSize=5;

		private static final Short newsIsDeleted = 1;
		private static final Short newsNotDeleted = 0;
		
		private static final String defaultNewsType = "1";
		private static final String practiceNewsType = "2";
		private static final String recruitNewsType = "3";
		private static final String cultivateNewsType = "4";
		private static final String degreeNewsType = "5";
		private static final String JobhuntingNewsType = "6";
		private static final String StubusinessNewsType = "7";
		private static final String OtherNewsType = "9";
		
		private static final int isHotCount = 100;	
		private static final int isNewMilliseconds = 1000 * 60 * 60 * 24 * 3;// 3 days 
		
		public static int getPageSize() {
			return pageSize;
		}
		public static Short getNewsIsDeleted() {
			return newsIsDeleted;
		}
		public static Short getNewsNotDeleted() {
			return newsNotDeleted;
		}
		public static String getDefaultNewsType() {
			return defaultNewsType;
		}
		public static String getPracticeNewsType() {
			return practiceNewsType;
		}
		public static String getRecruitNewsType() {
			return recruitNewsType;
		}
		public static String getCultivateNewsType() {
			return cultivateNewsType;
		}
		public static String getDegreeNewsType() {
			return degreeNewsType;
		}
		public static String getJobhuntingNewsType() {
			return JobhuntingNewsType;
		}
		public static String getStubusinessNewsType() {
			return StubusinessNewsType;
		}
		public static String getOtherNewsType() {
			return OtherNewsType;
		}
		public static int getIsHotCount() {
			return isHotCount;
		}
		public static int getIsNewMilliseconds() {
			return isNewMilliseconds;
		}
		public static String getCHECK_PASS() {
			return CHECK_PASS;
		}
		public static String getNOT_CHECK_PASS() {
			return NOT_CHECK_PASS;
		}
		public static int getListPageSize() {
			return listPageSize;
		}

	}
