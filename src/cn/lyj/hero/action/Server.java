package cn.lyj.hero.action;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lyj.hero.I;
import cn.lyj.hero.I.ExamGrade;
import cn.lyj.hero.bean.Result;
import cn.lyj.hero.bean.WordContentBean;
import cn.lyj.hero.entity.AreasBean;
import cn.lyj.hero.entity.ClassExamGradeBean;
import cn.lyj.hero.entity.ClassObj;
import cn.lyj.hero.entity.CourseBean;
import cn.lyj.hero.entity.ExamBean;
import cn.lyj.hero.entity.ExamClassGradeBean;
import cn.lyj.hero.entity.ExamGradeAvgBean;
import cn.lyj.hero.entity.ExerciseBean;
import cn.lyj.hero.entity.StartTimeBean;
import cn.lyj.hero.entity.SuperUser;
import cn.lyj.hero.entity.UserBean;
import cn.lyj.hero.service.Service;
import cn.lyj.hero.service.ServiceImpl;
import cn.lyj.hero.utils.SHA1;
import cn.lyj.hero.utils.StringFormatUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet(urlPatterns="/Server")
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Service service = new ServiceImpl();
    public Server() {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(I.UTF_8);
		String req = request.getParameter("request");
		System.out.println("request="+req);
		doPost(request, response);
		switch (req) {   
			case "getAreas": //2 1
				doGetAreas(request,response);
				break;
			case "getCourse": //2 1
				doGetCourse(request,response);
				break;
			case "getClassList"://2 1
				dogetClassList(request,response);
				break;
			case "getClassStartTime"://2 1
				doGetClassStartTime(request,response);
				break;
			case "getExamList"://2 1
				doGetExamList(request,response);
				break;
			case "getTenGrade": //2 1
				doGetTenGrade(request,response);
				break;
			case "sortGradeInClass"://2 1
				doSortGradeInClass(request,response);
				break;
			case "sortGradeInCourse"://2 1
				doSortGradeInCourse(request,response);
				break;
			case "sortGradeInTime"://2 1
				doSortGradeInTime(request,response);
				break;
			case "getSortInClass"://2 1
				doGetSortInClass(request,response);
				break;
			case "getSortInTime"://2 1
				doGetSortInTime(request,response);
				break;
			case "getSortInCourse"://2 1
				doGetSortInCourse(request,response);
				break;
			case "getExamNow"://2 1
				doGetExamNow(request,response);
				break;
			case "getExamGrade"://2 1
				doGetExamGrade(request,response);
				break;
			case "getAvgExamGrade"://2 1
				doGetAvgExamGrade(request,response);
				break;
			case "getClassGradeList"://2 1
				doGetClassGradeList(request,response);
				break;
			case "getClassNameById":
				doGetClassNameById(request,response);
				break;
			case "getNearlyGradeByUid":
				doGetNearlyGradeByUid(request,response);
				break;
			case "getWordContent":
				doGetWordContent(request,response);
				break;
		}
	}
    
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding(I.UTF_8);
		String req = request.getParameter("request");
		System.out.println("request="+req);
		switch (req) {   
		case "superUserlogin"://1
			doSuperUserLogin(request, response);
			break;
		case "superUserRegister"://1
			doSuperUserRegister(request,response);
			break;
		case "userRegister"://2
			doUserRegister(request,response);
			break;
		case "upload_avatar"://2
			doUploadAvatar(request,response);
			break;
		case "userLogin"://2
			doUserLogin(request,response);
			break;
		case "addArea"://1
			doAddArea(request,response);
			break;
		case "modArea"://1
			doModArea(request,response);
			break;
		case "delArea"://1
			doDelArea(request,response);
			break;
		case "addClass"://1
			doAddClass(request,response);
			break;
		case "modClass"://1
			doModClass(request,response);
			break;
		case "delClass"://1
			doDelClass(request,response);
			break;
		case "addStartTime"://1
			doAddStartTime(request,response);
			break;
		case "delStartTime"://1
			doDelStartTime(request,response);
			break;
		case "addCourse"://1
			doAddCourse(request,response);
			break;
		case "modCourse"://1
			doModCourse(request,response);
			break;
		case "delCourse"://1
			doDelCourse(request,response);
			break;
		case "addExam"://1
			doAddExam(request,response);
			break;
		case "modExam"://1
			doModExam(request,response);
			break;
		case "delExam"://1
			doDelExam(request,response);
			break;
		case "addExercise"://1 /2
			doAddExercise(request,response);
			break;
		case "modExamStatus"://1
			doModExamState(request,response);
			break;
		case "examNowByName"://1 /2
			doExamNowById(request,response);
			break;
		case "addExamGrade"://1
			doAddExamGrade(request,response);
			break;
		case "updateUser":
			doUpdateUser(request,response);
			break;
		case "uploadBestGrade":
			doUploadBestGrade(request,response);
			break;
		case "resetSuperUserPwd":
			
			break;
		case "resetUserPwd":
			
			break;
		}
	}
	/**
	 * 上传最好成绩
	 * @param request
	 * @param response
	 */
	private void doUploadBestGrade(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String uid = request.getParameter(I.User.UID);
		int grade = Integer.parseInt(request.getParameter(I.User.TOP_GRADE));
		Result res = new Result();
		ObjectMapper om = new ObjectMapper();
		if(service.uploadBestGrade(uid,grade)){
			res.setFlag(true);
			res.setMsg("修改成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				res.setFlag(false);
				res.setMsg("修改失败");
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 修改学生信息
	 * @param request
	 * @param response
	 */
	private void doUpdateUser(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String uid = request.getParameter(I.User.UID);
		String pwd = SHA1.SHA1Digest(request.getParameter(I.User.PWD)+uid+I.SECRET);
		String user_name = StringFormatUtils.getUTF_8Str(request, I.User.USER_NAME);
		Result res = new Result();
		ObjectMapper om = new ObjectMapper();
		if(service.updateUser(uid,pwd,user_name)){
			res.setFlag(true);
			res.setMsg("修改成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				res.setFlag(false);
				res.setMsg("修改失败");
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 下载数据
	 * @param request
	 * @param response
	 */
	private void doGetWordContent(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		ArrayList<WordContentBean> gradeList = service.getWordContent();
		if(gradeList!=null){
			ObjectMapper om =new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), gradeList);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 根据班级id获取班级名称
	 * @param request
	 * @param response
	 */
	private void doGetClassNameById(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int classId = Integer.parseInt(request.getParameter("id"));
		String className = service.getClassNameById(classId);
		if(className!=null){
			ObjectMapper om =new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), className);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取某场考试各班学生成绩
	 * @param request
	 * @param response
	 */
	private void doGetClassGradeList(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int exam_id = Integer.parseInt(request.getParameter(I.ExamGrade.EXAM_ID));
		int b_class = Integer.parseInt(request.getParameter(I.ExamGrade.B_CLASS));
		ArrayList<ExamClassGradeBean> gradeList = service.getClassGradeList(exam_id,b_class);
		if(gradeList!=null){
			ObjectMapper om =new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), gradeList);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 获取某场考试各班学生平均成绩排名
     * @param request
     * @param response
     */
	private void doGetAvgExamGrade(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int exam_id = Integer.parseInt(request.getParameter(I.ExamGrade.EXAM_ID));
		ArrayList<ExamGradeAvgBean> gradeList = service.getAvgExamGrade(exam_id);
		if(gradeList!=null){
			ObjectMapper om =new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), gradeList);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取考试成绩排行表
	 * @param response 
	 * @param request 
	 */
	private void doGetExamGrade(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int exam_id = Integer.parseInt(request.getParameter(I.ExamGrade.EXAM_ID));
		ArrayList<ClassExamGradeBean> gradeList = service.getExamGrade(exam_id);
		if(gradeList!=null){
			ObjectMapper om =new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), gradeList);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 新增考试成绩
	 * @param request
	 * @param response
	 */
	private void doAddExamGrade(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int exam_id = Integer.parseInt(request.getParameter(I.ExamGrade.EXAM_ID));
		int grade = Integer.parseInt(request.getParameter(I.ExamGrade.GRADE));
		String time = request.getParameter(I.ExamGrade.SUBMIT_TIME);
		String user_name = StringFormatUtils.getUTF_8Str(request, I.ExamGrade.USER_NAME);
		int b_class = Integer.parseInt(request.getParameter(I.ExamGrade.B_CLASS));
		String course_id = request.getParameter(I.ExamGrade.COURSE_ID);
		String b_start_time = request.getParameter(I.ExamGrade.B_START_TIME);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.examNowById(exam_id)){
			if(service.addExamGrade(exam_id,grade,time,user_name,b_class,course_id,b_start_time)){
				res.setFlag(true);
				res.setMsg("提交成功");
				try {
					om.writeValue(response.getOutputStream(), res);
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				res.setFlag(false);
				res.setMsg("提交失败");
				try {
					om.writeValue(response.getOutputStream(), res);
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			res.setFlag(false);
			res.setMsg("考试已结束");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 判断考试是否正在进行
	 * @param request
	 * @param response
	 */
	private void doExamNowById(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int id = Integer.parseInt(request.getParameter(I.Exam.ID));
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.examNowById(id)){
			res.setFlag(true);
			res.setMsg("考试正在进行");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("考试已结束");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 根据考试名修改考试状态
	 * @param request
	 * @param response
	 */
	private void doModExamState(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String exam_name = StringFormatUtils.getUTF_8Str(request, I.Exam.EXAM_NAME);
		Integer exam_state = Integer.parseInt(request.getParameter(I.Exam.STATE));
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.modExamState(exam_name,exam_state)){
			res.setFlag(true);
			res.setMsg("修改考试状态成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("修改失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 获取正在进行的考试
	 * @param request
	 * @param response
	 */
	private void doGetExamNow(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		ArrayList<ExamBean> exam_list = service.getExamNow();
		if(exam_list!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), exam_list);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 获取学科成绩排行
     * @param request
     * @param response
     */
	private void doGetSortInCourse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String course_id = request.getParameter(I.Exercise.COURSE_ID);
		ArrayList<ExerciseBean> exercise_list = service.getSortInCourse(course_id);
		if(exercise_list!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), exercise_list);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 获取同期学员成绩排名
     * @param request
     * @param response
     */
	private void doGetSortInTime(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String time = request.getParameter(I.Exercise.START_TIME);
		ArrayList<ExerciseBean> exercise_list = service.getSortInTime(time);
		if(exercise_list!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), exercise_list);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
     * 根据班级获取本班打字排行
     * @param request
     * @param response
     */
	private void doGetSortInClass(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int classNum = Integer.parseInt(request.getParameter(I.Exercise.B_CLASS));
		ArrayList<ExerciseBean> exercise_list = service.getSortInClass(classNum);
		if(exercise_list!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), exercise_list);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 获取学期排名
     * @param request
     * @param response
     */
	private void doSortGradeInTime(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int grade = Integer.parseInt(request.getParameter(I.Exercise.GRADE));
		String start_time = request.getParameter(I.Exercise.START_TIME);
		int count = service.SortGradeInTime(grade,start_time);
		if(count!=0){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), count);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 获取学科排名
     * @param request
     * @param response
     */
	private void doSortGradeInCourse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int grade = Integer.parseInt(request.getParameter(I.Exercise.GRADE));
		String course_id = request.getParameter(I.Exercise.COURSE_ID);
		int count = service.SortGradeInCourse(grade,course_id);
		if(count!=0){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), count);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     *获取班级排名
     * @param request
     * @param response
     */
	private void doSortGradeInClass(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int grade = Integer.parseInt(request.getParameter(I.Exercise.GRADE));
		int classNum = Integer.parseInt(request.getParameter(I.Exercise.B_CLASS));
		int count = service.SortGradeinClass(grade,classNum);
		if(count!=0){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), count);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 获取某人最近的十次练习成绩
	 * @param request
	 * @param response
	 */
	private void doGetNearlyGradeByUid(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String userName = StringFormatUtils.getUTF_8Str(request, I.Exercise.USER_NAME);
		ArrayList<ExerciseBean> exerciseList = service.getNearlyExercises(userName);
		if(exerciseList!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), exerciseList);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
     * 根据学生姓名获取最佳的10个成绩
     * @param request
     * @param response
     */
	private void doGetTenGrade(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String user_name = StringFormatUtils.getUTF_8Str(request, I.Exercise.USER_NAME);
		ArrayList<ExerciseBean> exerciseList = service.getExercises(user_name);
		if(exerciseList!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), exerciseList);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 新增日常打字练习成绩
	 * @param request
	 * @param response
	 */
	private void doAddExercise(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		int grade = Integer.parseInt(request.getParameter(I.Exercise.GRADE));
		String exe_time = request.getParameter(I.Exercise.EXE_TIME);
		String user_name = StringFormatUtils.getUTF_8Str(request, I.Exercise.USER_NAME);
		String course_id = request.getParameter(I.Exercise.COURSE_ID);
		int b_class = Integer.parseInt(request.getParameter(I.Exercise.B_CLASS));
		String b_start_time = request.getParameter(I.Exercise.START_TIME);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.addExerciseGrade(grade,exe_time,user_name,course_id,b_class,b_start_time)){
			res.setFlag(true);
			res.setMsg("添加成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("添加失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 获取考试列表
     * @param request
     * @param response
     */
	private void doGetExamList(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		ArrayList<ExamBean> exam_list = service.getExamList();
		if(exam_list!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), exam_list);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 删除考试
	 * @param request
	 * @param response
	 */
	private void doDelExam(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String mark = request.getParameter(I.Mark.MARK);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.delExam(mark)){
			res.setFlag(true);
			res.setMsg("删除成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("删除失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 修改考试
	 * @param request
	 * @param response
	 */
	private void doModExam(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String exam_name = request.getParameter(I.Exam.EXAM_NAME);
		String exam_time = request.getParameter(I.Exam.EXAM_TIME);
		String course_id = request.getParameter(I.Exam.COURSE_ID);
		String mark = StringFormatUtils.getUTF_8Str(request, I.Mark.MARK);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.modExam(exam_name,exam_time,course_id,mark)){
			res.setFlag(true);
			res.setMsg("修改成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("修改失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 添加考试
	 * @param request
	 * @param response
	 */
	private void doAddExam(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String exam_name = StringFormatUtils.getUTF_8Str(request, I.Exam.EXAM_NAME);
		String exam_time = request.getParameter(I.Exam.EXAM_TIME);
		String course_id = request.getParameter(I.Exam.COURSE_ID);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.addExam(exam_name,exam_time,course_id)){
			res.setFlag(true);
			res.setMsg("添加成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("添加失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 删除课程类型
	 * @param request
	 * @param response
	 */
	private void doDelCourse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String simple_name = request.getParameter(I.Course.SIMPLE_NAME);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.delCourse(simple_name)){
			res.setFlag(true);
			res.setMsg("删除成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("删除失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
	}
	/**
	 * 修改课程类型
	 * @param request
	 * @param response
	 */
	private void doModCourse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String simple_name = request.getParameter(I.Course.SIMPLE_NAME);
		String course_name = StringFormatUtils.getUTF_8Str(request, I.Course.COURSE_NAME);
		String mark = request.getParameter(I.Mark.MARK);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.modCourse(simple_name,course_name,mark)){
			res.setFlag(true);
			res.setMsg("修改成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("修改失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 添加课程类型
	 * @param request
	 * @param response
	 */
    private void doAddCourse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String simple_name = request.getParameter(I.Course.SIMPLE_NAME);
		String course_name = StringFormatUtils.getUTF_8Str(request, I.Course.COURSE_NAME);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.addCourse(simple_name,course_name)){
			res.setFlag(true);
			res.setMsg("添加成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("添加失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
     * 删除开班时间
     * @param request
     * @param response
     */
    private void doDelStartTime(HttpServletRequest request,
			HttpServletResponse response) {
    	response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String start_time= request.getParameter(I.StartTime.START_TIME);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.delStartTime(start_time)){
			res.setFlag(true);
			res.setMsg("删除成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("删除失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 添加开班时间
     * @param request
     * @param response
     */
    private void doAddStartTime(HttpServletRequest request,
			HttpServletResponse response) {
    	response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String start_time= request.getParameter(I.StartTime.START_TIME);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.addStartTime(start_time)){
			res.setFlag(true);
			res.setMsg("添加成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("添加失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 删除班级
     * @param request
     * @param response
     */
    private void doDelClass(HttpServletRequest request,
			HttpServletResponse response) {
    	response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String simple_name = request.getParameter(I.IClass.SIMPLE_NAME);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.delClass(simple_name)){
			res.setFlag(true);
			res.setMsg("删除成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("删除失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 修改班级
     * @param request
     * @param response
     */
    private void doModClass(HttpServletRequest request,
			HttpServletResponse response) {
    	response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
    	String class_name = request.getParameter(I.IClass.CLASS_NAME);
		String b_area = request.getParameter(I.IClass.B_AREA);
		String b_course = request.getParameter(I.IClass.B_COURSE);
		String start_time = request.getParameter(I.IClass.START_TIME);
		String class_number = request.getParameter(I.IClass.CLASS_NUMBER);
		String simple_name = request.getParameter(I.IClass.SIMPLE_NAME);
		String mark = request.getParameter(I.Mark.MARK);
		int class_num = Integer.parseInt(class_number);
		ClassObj obj = new ClassObj(0, class_name, b_area, b_course, start_time,class_num, simple_name);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.modClass(obj,mark)){
			res.setFlag(true);
			res.setMsg("修改成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("修改失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
     * 添加班级
     * @param request
     * @param response
     */
    private void doAddClass(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String class_name = request.getParameter(I.IClass.CLASS_NAME);
		String b_area = request.getParameter(I.IClass.B_AREA);
		String b_course = request.getParameter(I.IClass.B_COURSE);
		String start_time = request.getParameter(I.IClass.START_TIME);
		String class_number = request.getParameter(I.IClass.CLASS_NUMBER);
		String simple_name = request.getParameter(I.IClass.SIMPLE_NAME);
		int class_num = Integer.parseInt(class_number);
		ClassObj obj = new ClassObj(0, class_name, b_area, b_course, start_time,class_num, simple_name);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.addClass(obj)){
			res.setFlag(true);
			res.setMsg("添加成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("添加失败");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 删除校区
     * @param request
     * @param response
     */
    private void doDelArea(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String simple_name = request.getParameter(I.Area.SIMPLE_NAME);
		Result res = new Result();
		ObjectMapper om =new ObjectMapper();
		if(service.delArea(simple_name)){
			res.setFlag(true);
			res.setMsg("删除成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			res.setFlag(false);
			res.setMsg("删除失败，需先删除关联数据");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
     * 修改校区
     * @param request
     * @param response
     */
    private void doModArea(HttpServletRequest request,
			HttpServletResponse response) {
    	response.setContentType(I.CONTENT_TYPE);
    	response.setCharacterEncoding(I.UTF_8);
    	String simple_name = request.getParameter(I.Area.SIMPLE_NAME);
    	String area_name = StringFormatUtils.getUTF_8Str(request, I.Area.AREA_NAME);
    	Result res = new Result();
    	ObjectMapper om = new ObjectMapper();
    	if(service.modArea(simple_name,area_name)){
    		try {
    			res.setFlag(true);
    			res.setMsg("修改成功");
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else{
    		try {
    			res.setFlag(false);
    			res.setMsg("修改失败");
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}
	/**
     * 添加校区
     * @param request
     * @param response
     */
    private void doAddArea(HttpServletRequest request,
			HttpServletResponse response) {
    	response.setContentType(I.CONTENT_TYPE);
    	response.setCharacterEncoding(I.UTF_8);
    	String simple_name = request.getParameter("simple_name");
    	String area_name = StringFormatUtils.getUTF_8Str(request, "area_name");
    	Result res = new Result();
    	ObjectMapper om = new ObjectMapper();
    	if(service.addArea(simple_name,area_name)){
    		res.setFlag(true);
    		res.setMsg("添加成功");
    		try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else{
    		res.setFlag(false);
    		res.setMsg("添加失败");
    		try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}
	/**
     * 用户登录
     * @param request
     * @param response
     */
	private void doUserLogin(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String uid = request.getParameter("uid");
		String pwd = SHA1.SHA1Digest(request.getParameter(I.User.PWD)+uid+I.SECRET);
		UserBean user = service.userLogin(uid,pwd);
		if(user !=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), user); 
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 *获取开班日期
	 * @param request
	 * @param response
	 */
	private void doGetClassStartTime(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		ArrayList<StartTimeBean> start_time_list = service.getStartTimeList();
		if(start_time_list!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), start_time_list);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 通过所属校区和课程类型获取班级列表
	 * @param request
	 * @param response
	 */
	private void dogetClassList(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String area_simple_name = request.getParameter(I.IClass.B_AREA);
		String course_simple_name = request.getParameter(I.IClass.B_COURSE);
		String start_time = request.getParameter(I.IClass.START_TIME);
		ArrayList<ClassObj> classList = service.getClassList(area_simple_name,course_simple_name,start_time);
		if(classList!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), classList);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取课程类型列表
	 * @param request
	 * @param response
	 */
	private void doGetCourse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		ArrayList<CourseBean> courses = service.getCourses();
		if(courses!=null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), courses);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取校区列表
	 * @param request
	 * @param response
	 */
	private void doGetAreas(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		ArrayList<AreasBean> areas = service.getAreas();
		if(areas != null){
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), areas);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 上传头像
     * @param request
     * @param response
     */
	private void doUploadAvatar(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String uid = request.getParameter(I.User.UID);
		Result res = new Result();
		if(service.upload_avatar(uid,request)){
			res.setMsg("头像上传成功");
			res.setFlag(true);
		}else{
			res.setMsg("头像上传失败");
			res.setFlag(false);
		}
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), res);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 普通用户注册
	 * @param request
	 * @param response
	 */
	private void doUserRegister(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String uid = request.getParameter(I.User.UID);
		String pwd = SHA1.SHA1Digest(request.getParameter(I.User.PWD)+uid+I.SECRET);
		String user_name = StringFormatUtils.getUTF_8Str(request, I.User.USER_NAME);
		int sex = Integer.parseInt(request.getParameter(I.User.SEX));
		int b_class = Integer.parseInt(request.getParameter(I.User.B_CLASS));
		int top_grade = Integer.parseInt(request.getParameter(I.User.TOP_GRADE));
		UserBean user = new UserBean(uid,pwd,user_name,sex,b_class,top_grade,null);
		Result res = new Result();
		ObjectMapper om = new ObjectMapper();
		if(service.registerUser(user)){
			res.setFlag(true);
			res.setMsg("注册成功");
			try {
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				res.setFlag(false);
				res.setMsg("注册失败");
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 超级用户注册方法
	 * 用户密码使用用户名+密码+秘钥的方式组合后使用SHA1算法加密
	 * @param request
	 * @param response
	 */
	private void doSuperUserRegister(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String userName = request.getParameter(I.Super_User.USER_NAME);
		String passWord = SHA1.SHA1Digest(request.getParameter(I.Super_User.USER_PWD)+userName+I.SECRET);
		String userRemark = StringFormatUtils.getUTF_8Str(request, I.Super_User.USER_REMARK);
		String token = request.getParameter(I.Super_User.REGISTER_CODE);
		Result res = new Result();
		ObjectMapper om = new ObjectMapper();
		if (token.equals(I.REGISTER_TOKEN)) {
			SuperUser user = new SuperUser();
			user.setUserName(userName);
			user.setPassWord(passWord);
			user.setUserRemark(userRemark);
			try {
				if (service.registerSuperUser(user)) {
					try {
						res.setFlag(true);
						res.setMsg("注册成功");
						om.writeValue(response.getOutputStream(), res);
					} catch (JsonGenerationException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					try {
						res.setFlag(false);
						res.setMsg("注册失败");
						om.writeValue(response.getOutputStream(), res);
					} catch (JsonGenerationException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				res.setFlag(false);
				res.setMsg("验证失败");
				om.writeValue(response.getOutputStream(), res);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 登录
	 * @param request
	 * @param response
	 */
	private void doSuperUserLogin(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(I.CONTENT_TYPE);
		response.setCharacterEncoding(I.UTF_8);
		String userName = request.getParameter(I.Super_User.USER_NAME);
		String passWord = SHA1.SHA1Digest(request.getParameter(I.Super_User.USER_PWD)+userName+I.SECRET);
		System.out.println(userName+"\n"+passWord);
		Result res = new Result();
		res.setFlag(false);
		res.setMsg("登录失败");
		SuperUser user = new SuperUser();
		user.setUserName(userName);
		user.setPassWord(passWord);
		try {
			SuperUser resUser = service.superUserlogin(user);
			ObjectMapper om = new ObjectMapper();
			if (resUser!=null) {
				try {
					om.writeValue(response.getOutputStream(), resUser);
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				try {
					om.writeValue(response.getOutputStream(), res);
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
	
	
