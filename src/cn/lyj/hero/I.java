package cn.lyj.hero;

public class I {
	public static final String CONTENT_TYPE = "text/html;charset=utf-8";
	public static final String UTF_8 = "utf-8";
	public static final String SECRET = "nviKSJdoSJLKDJaiosjIOjkljdWDas";
	public static final String REGISTER_TOKEN = "980804224";
	public interface Super_User{
		public static final String USER_NAME = "user_name";
		public static final String USER_PWD = "user_pwd";
		public static final String USER_REMARK = "user_remark";
		public static final String REGISTER_CODE = "code";
	}
	
	public interface User{
		public static final String UID = "uid";
		public static final String PWD = "pwd";
		public static final String USER_NAME = "user_name";
		public static final String SEX = "sex";
		public static final String B_CLASS = "b_class";
		public static final String AVATAR = "avatar";
		public static final String TOP_GRADE = "top_grade";
	}
	
	public interface Area{
		public static final String SIMPLE_NAME = "simple_name";
		public static final String AREA_NAME = "area_name";
	}
	
	public interface IClass{
		public static final String CLASS_NAME = "class_name";
		public static final String B_AREA = "b_area";
		public static final String B_COURSE = "b_course";
		public static final String START_TIME = "start_time";
		public static final String CLASS_NUMBER = "class_number";
		public static final String SIMPLE_NAME = "simple_name";
	}
	
	public interface Mark{
		public static final String MARK = "mark";
	}
	
	public interface StartTime{
		public static final String START_TIME = "start_time";
	}
	
	public interface Course{
		public static final String SIMPLE_NAME = "simple_name";
		public static final String COURSE_NAME = "course_name";
	}
	
	public interface Exam{
		public static final String EXAM_NAME = "exam_name";
		public static final String EXAM_TIME = "exam_time";
		public static final String COURSE_ID = "course_id";
		public static final String STATE = "status";
		public static final String ID = "id";
	}
	
	public interface Exercise{
		public static final String GRADE = "grade";
		public static final String EXE_TIME = "exe_time";
		public static final String USER_NAME = "user_name";
		public static final String COURSE_ID = "course_id";
		public static final String B_CLASS = "b_class";
		public static final String START_TIME = "start_time";
	}
	
	public interface ExamGrade{
		public static final String EXAM_ID = "exam_id";
		public static final String GRADE = "grade";
		public static final String SUBMIT_TIME = "submit_time";
		public static final String USER_NAME = "user_name";
		public static final String B_CLASS = "b_class";
		public static final String COURSE_ID = "course_id";
		public static final String B_START_TIME = "b_start_time";
	}
	
	public interface ClassExamGrade{
		public static final String EXAM_ID = "exam_id";
		public static final String USER_NAME = "user_name";
		public static final String GRADE = "grade";
		public static final String SUBMIT_TIME = "submit_time";
		public static final String CLASS_NAME = "class_name";
	}
	public interface WordContent{
		public static final String ID = "id";
		public static final String WORD = "word";
		public static final String COURSE_ID = "course_id";
	}
}
