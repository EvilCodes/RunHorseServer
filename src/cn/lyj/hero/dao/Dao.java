package cn.lyj.hero.dao;

import java.util.ArrayList;
import java.util.List;

import cn.lyj.hero.bean.WordContentBean;
import cn.lyj.hero.entity.AreasBean;
import cn.lyj.hero.entity.ClassExamGradeBean;
import cn.lyj.hero.entity.ClassObj;
import cn.lyj.hero.entity.CourseBean;
import cn.lyj.hero.entity.ExamBean;
import cn.lyj.hero.entity.ExamClassGradeBean;
import cn.lyj.hero.entity.ExamGradeAvgBean;
import cn.lyj.hero.entity.ExamGradeBean;
import cn.lyj.hero.entity.ExerciseBean;
import cn.lyj.hero.entity.StartTimeBean;
import cn.lyj.hero.entity.SuperUser;
import cn.lyj.hero.entity.UserBean;



/**
 * 
 * @author liuyujie
 */
public interface Dao {

	SuperUser superUserlogin(SuperUser user);

	boolean findUserByUserName(String user);

	boolean registerSuperUser(SuperUser user);

	boolean registerUser(UserBean user);

	boolean upload_avatar(String uid);

	ArrayList<AreasBean> getAreas();

	ArrayList<CourseBean> getCourses();

	ArrayList<ClassObj> getClassList(String area_simple_name,
			String course_simple_name,String start_time);

	ArrayList<StartTimeBean> getStartTimeList();

	UserBean userLogin(String uid, String pwd);

	boolean addArea(String simple_name, String area_name);

	boolean modArea(String simple_name, String area_name);

	boolean delArea(String simple_name);

	boolean addClass(ClassObj obj);

	boolean modClass(ClassObj obj, String mark);

	boolean delClass(String simple_name);

	boolean addStartTime(String start_time);

	boolean delStartTime(String start_time);

	boolean addCourse(String simple_name, String course_name);

	boolean modCourse(String simple_name, String course_name, String mark);

	boolean delCourse(String simple_name);

	boolean addExam(String exam_name, String exam_time, String course_id);

	boolean modExam(String exam_name, String exam_time, String course_id,
			String mark);

	boolean delExam(String mark);

	ArrayList<ExamBean> getExamList();

	boolean addExerciseGrade(int grade, String exe_time, String user_name,
			String course_id, int b_class, String start_time);

	ArrayList<ExerciseBean> getExercises(String user_name);

	int SortGradeinClass(int grade, int classNum);

	int SortGradeInCourse(int grade, String course_id);

	int SortGradeInTime(int grade, String start_time);

	ArrayList<ExerciseBean> getSortInClass(int classNum);

	ArrayList<ExerciseBean> getSortInTime(String time);

	ArrayList<ExerciseBean> getSortInCourse(String course_id);

	ArrayList<ExamBean> getExamNow();

	boolean modExamState(String exam_name, Integer exam_state);

	boolean examNowById(int id);

	boolean addExamGrade(int exam_id, int grade, String time, String user_name,
			int b_class, String course_id, String b_start_time);

	ArrayList<ClassExamGradeBean> getExamGrade(int exam_id);

	ArrayList<ExamGradeAvgBean> getAvgExamGrade(int exam_id);

	ArrayList<ExamClassGradeBean> getClassGradeList(int exam_id, int b_class);

	String getClassNameById(int classId);

	ArrayList<ExerciseBean> getNearlyExercises(String uid);

	ArrayList<WordContentBean> getWordContent();

	boolean updateUser(String uid, String pwd, String user_name);

	boolean uploadBestGrade(String uid, int grade);

}
