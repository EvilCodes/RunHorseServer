package cn.lyj.hero.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import cn.lyj.hero.bean.WordContentBean;
import cn.lyj.hero.dao.Dao;
import cn.lyj.hero.dao.DaoImpl;
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
import cn.lyj.hero.utils.PropertiesUtil;

/**
 * 
 * @author liuyujie
 *
 */
public class ServiceImpl implements Service  {
	private Dao dao = new DaoImpl();
	
	@Override
	public SuperUser superUserlogin(SuperUser user) {
		SuperUser resUser = null;
		if((resUser = dao.superUserlogin(user))!=null){
			return resUser;
		}
		return null;
	}

	@Override
	public boolean registerSuperUser(SuperUser user) {
		if(dao.registerSuperUser(user)){
			return true;
		}
		return false;
	}

	@Override
	public boolean registerUser(UserBean user) {
		if(dao.registerUser(user)){
			return true;
		}
		return false;
	}

	@Override
	public boolean upload_avatar(String uid, HttpServletRequest request) {
		File file = new File(PropertiesUtil.getValue("avatarpath")+uid+".jpg");
		BufferedOutputStream bos = null;
		InputStream is = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			byte[] bArr = new byte[1024*8];
			is = request.getInputStream();
			int b = 0;
			while((b=is.read(bArr))!=-1){
				bos.write(bArr, 0, b);
			}
			bos.flush();
			if(bos!=null){
				bos.close();
			}
			if(is!= null){
				is.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if(dao.upload_avatar(uid)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public ArrayList<AreasBean> getAreas() {
		ArrayList<AreasBean> areas = null;
		if((areas = dao.getAreas())!=null){
			return areas;
		}
		return null;
	}

	@Override
	public ArrayList<CourseBean> getCourses() {
		ArrayList<CourseBean> courses = null;
		if((courses = dao.getCourses())!=null){
			return courses;
		}
		return null;
	}

	@Override
	public ArrayList<ClassObj> getClassList(String area_simple_name,
			String course_simple_name,String start_time) {
		ArrayList<ClassObj> classList = null;
		if((classList = dao.getClassList(area_simple_name,course_simple_name,start_time))!=null){
			return classList;
		}
		return null;
	}

	@Override
	public ArrayList<StartTimeBean> getStartTimeList() {
		ArrayList<StartTimeBean> start_time_list = null;
		if((start_time_list = dao.getStartTimeList())!=null){
			return start_time_list;
		}
		return null;
	}

	@Override
	public UserBean userLogin(String uid, String pwd) {
		UserBean user = null;
		if((user = dao.userLogin(uid,pwd))!=null){
			return user;
		}
		return null;
	}

	@Override
	public boolean addArea(String simple_name, String area_name) {
		if(dao.addArea(simple_name,area_name)){
			return true;
		}
		return false;
	}

	@Override
	public boolean modArea(String simple_name, String area_name) {
		if(dao.modArea(simple_name,area_name)){
			return true;
		}
		return false;
	}

	@Override
	public boolean delArea(String simple_name) {
		if(dao.delArea(simple_name)){
			return true;
		}
		return false;
	}

	@Override
	public boolean addClass(ClassObj obj) {
		if(dao.addClass(obj)){
			return true;
		}
		return false;
	}

	@Override
	public boolean modClass(ClassObj obj,String mark) {
		if(dao.modClass(obj,mark)){
			return true;
		}
		return false;
	}

	@Override
	public boolean delClass(String simple_name) {
		if(dao.delClass(simple_name)){
			return true;
		}
		return false;
	}

	@Override
	public boolean addStartTime(String start_time) {
		if(dao.addStartTime(start_time)){
			return true;
		}
		return false;
	}

	@Override
	public boolean delStartTime(String start_time) {
		if(dao.delStartTime(start_time)){
			return true;
		}
		return false;
	}

	@Override
	public boolean addCourse(String simple_name, String course_name) {
		if(dao.addCourse(simple_name,course_name)){
			return true;
		}
		return false;
	}

	@Override
	public boolean modCourse(String simple_name, String course_name, String mark) {
		if(dao.modCourse(simple_name,course_name,mark)){
			return true;
		}
		return false;
	}

	@Override
	public boolean delCourse(String simple_name) {
		if(dao.delCourse(simple_name)){
			return true;
		}
		return false;
	}

	@Override
	public boolean addExam(String exam_name, String exam_time, String course_id) {
		if(dao.addExam(exam_name,exam_time,course_id)){
			return true;
		}
		return false;
	}

	@Override
	public boolean modExam(String exam_name, String exam_time,
			String course_id, String mark) {
		if(dao.modExam(exam_name,exam_time,course_id,mark)){
			return true;
		}
		return false;
	}

	@Override
	public boolean delExam(String mark) {
		if(dao.delExam(mark)){
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<ExamBean> getExamList() {
		ArrayList<ExamBean> exam_list = null;
		if((exam_list = dao.getExamList())!=null){
			return exam_list;
		}
		return null;
	}

	@Override
	public boolean addExerciseGrade(int grade, String exe_time,
			String user_name, String course_id, int b_class,String start_time) {
		if(dao.addExerciseGrade(grade,exe_time,user_name,course_id,b_class,start_time)){
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<ExerciseBean> getExercises(String user_name) {
		ArrayList<ExerciseBean> exerciseList = null;
		if((exerciseList = dao.getExercises(user_name))!=null){
			return exerciseList;
		}
		return null;
	}

	@Override
	public int SortGradeinClass(int grade, int classNum) {
		int count = 0;
		if((count = dao.SortGradeinClass(grade,classNum))!=0){
			return count;
		}
		return 0;
	}

	@Override
	public int SortGradeInCourse(int grade, String course_id) {
		int count = 0;
		if((count = dao.SortGradeInCourse(grade,course_id))!=0){
			return count;
		}
		return 0;
	}

	@Override
	public int SortGradeInTime(int grade, String start_time) {
		int count = 0;
		if((count = dao.SortGradeInTime(grade,start_time))!=0){
			return count;
		}
		return 0;
	}

	@Override
	public ArrayList<ExerciseBean> getSortInClass(int classNum) {
		ArrayList<ExerciseBean> exercise_list = null;
		if((exercise_list = dao.getSortInClass(classNum))!=null){
			return exercise_list;
		}
		return null;
	}

	@Override
	public ArrayList<ExerciseBean> getSortInTime(String time) {
		ArrayList<ExerciseBean> exercise_list = null;
		if((exercise_list = dao.getSortInTime(time))!=null){
			return exercise_list;
		}
		return null;
	}

	@Override
	public ArrayList<ExerciseBean> getSortInCourse(String course_id) {
		ArrayList<ExerciseBean> exercise_list = null;
		if((exercise_list = dao.getSortInCourse(course_id))!=null){
			return exercise_list;
		}
		return null;
	}

	@Override
	public ArrayList<ExamBean> getExamNow() {
		ArrayList<ExamBean> exam_list = null;
		if((exam_list = dao.getExamNow())!=null){
			return exam_list;
		}
		return null;
	}

	@Override
	public boolean modExamState(String exam_name, Integer exam_state) {
		if(dao.modExamState(exam_name,exam_state)){
			return true;
		}
		return false;
	}

	@Override
	public boolean examNowById(int id) {
		if(dao.examNowById(id)){
			return true;
		}
		return false;
	}

	@Override
	public boolean addExamGrade(int exam_id, int grade, String time,
			String user_name, int b_class, String course_id,String b_start_time) {
		if(dao.addExamGrade(exam_id,grade,time,user_name,b_class,course_id,b_start_time)){
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<ClassExamGradeBean> getExamGrade(int exam_id) {
		ArrayList<ClassExamGradeBean> gradeList = null;
		if((gradeList = dao.getExamGrade(exam_id))!=null){
			return gradeList;
		}
		return null;
	}

	@Override
	public ArrayList<ExamGradeAvgBean> getAvgExamGrade(int exam_id) {
		ArrayList<ExamGradeAvgBean> gradeList = null;
		if((gradeList = dao.getAvgExamGrade(exam_id))!=null){
			return gradeList;
		}
		return null;
	}

	@Override
	public ArrayList<ExamClassGradeBean> getClassGradeList(int exam_id,
			int b_class) {
		ArrayList<ExamClassGradeBean> gradeList = null;
		if((gradeList = dao.getClassGradeList(exam_id,b_class))!=null){
			return gradeList;
		}
		return null;
	}

	@Override
	public String getClassNameById(int classId) {
		String className = null;
		if((className = dao.getClassNameById(classId))!=null){
			return className;
		}
		return null;
	}

	@Override
	public ArrayList<ExerciseBean> getNearlyExercises(String uid) {
		ArrayList<ExerciseBean> exerciseList = null;
		if((exerciseList = dao.getNearlyExercises(uid))!=null){
			return exerciseList;
		}
		return null;
	}

	@Override
	public ArrayList<WordContentBean> getWordContent() {
		ArrayList<WordContentBean> exerciseList = null;
		if((exerciseList = dao.getWordContent())!=null){
			return exerciseList;
		}
		return null;
	}

	@Override
	public boolean updateUser(String uid, String pwd, String user_name) {
		if(dao.updateUser(uid,pwd,user_name)){
			return true;
		}
		return false;
	}

	@Override
	public boolean uploadBestGrade(String uid, int grade) {
		if(dao.uploadBestGrade(uid,grade)){
			return true;
		}
		return false;
	}

}
