package cn.lyj.hero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.lyj.hero.I;
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
import cn.lyj.hero.utils.DBUtil;

/**
 * 
 * @author liuyujie
 */
public class DaoImpl implements Dao {
	@Override
	public SuperUser superUserlogin(SuperUser user) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		SuperUser resUser = null;
		String sql = "select * from t_super_user where user_name =? and user_pwd=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassWord());
			rs = ps.executeQuery();
			while(rs.next()){
				resUser = new SuperUser();
				resUser.setUserName(rs.getString("user_name"));
				resUser.setPassWord(rs.getString("user_pwd"));
				resUser.setUserRemark(rs.getString("user_remark"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return resUser;
	}

	@Override
	public boolean findUserByUserName(String optUser) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "select * from t_super_user where user_name =?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, optUser);
			rs = ps.executeQuery();
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return false;
	}

	@Override
	public boolean registerSuperUser(SuperUser user) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "INSERT INTO t_super_user(user_name,user_pwd,user_remark) VALUES(?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassWord());
			ps.setString(3, user.getUserRemark());
			return ps.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean registerUser(UserBean user) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "INSERT INTO t_user(uid,pwd,user_name,sex,b_class,top_grade) VALUES (?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getUid());
			ps.setString(2, user.getPwd());
			ps.setString(3, user.getUser_name());
			ps.setInt(4, user.getSex());
			ps.setInt(5, user.getB_class());
			ps.setInt(6, user.getTop_grade());
			return ps.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean upload_avatar(String uid) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "update t_user set avatar=? where uid=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1,"avatar/"+uid+".jpg");
			ps.setString(2, uid);
			return ps.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public ArrayList<AreasBean> getAreas() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "select * from t_area";
		ArrayList<AreasBean> areas = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				AreasBean area = new AreasBean();
				area.setSimple_name(rs.getString("simple_name"));
				area.setArea_name(rs.getString("area_name"));
				areas.add(area);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return areas;
	}

	@Override
	public ArrayList<CourseBean> getCourses() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "select * from t_course";
		ArrayList<CourseBean> courses = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CourseBean course = new CourseBean();
				course.setSimple_name(rs.getString("simple_name"));
				course.setCourse_name(rs.getString("course_name"));
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return courses;
	}

	@Override
	public ArrayList<ClassObj> getClassList(String area_simple_name,
			String course_simple_name,String start_time) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "select * from t_class where b_area=? and b_course=? and start_time=?";
		ArrayList<ClassObj> classList = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, area_simple_name);
			ps.setString(2, course_simple_name);
			ps.setString(3, start_time);
			rs = ps.executeQuery();
			while(rs.next()){
				ClassObj obj = new ClassObj();
				obj.setId(rs.getInt("id"));
				obj.setClass_name(rs.getString("class_name"));
				obj.setB_area(rs.getString("b_area"));
				obj.setB_course(rs.getString("b_course"));
				obj.setStart_time(rs.getString("start_time"));
				obj.setClass_number(rs.getInt("class_number"));
				obj.setSimple_name(rs.getString("simple_name"));
				classList.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return classList;
	}

	@Override
	public ArrayList<StartTimeBean> getStartTimeList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "select * from t_class_start_time";
		ArrayList<StartTimeBean> start_time_list = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				StartTimeBean start_time = new StartTimeBean();
				start_time.setStart_time(rs.getString("start_time"));
				start_time_list.add(start_time);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return start_time_list;
	}

	@Override
	public UserBean userLogin(String uid, String pwd) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "select * from t_user where uid=? and pwd=?";
		UserBean user = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			while(rs.next()){
				user = new UserBean();
				user.setUid(rs.getString("uid"));
				user.setPwd(rs.getString("pwd"));
				user.setUser_name(rs.getString("user_name"));
				user.setSex(rs.getInt("sex"));
				user.setB_class(rs.getInt("b_class"));
				user.setAvatar(rs.getString("avatar"));
				user.setTop_grade(rs.getInt("top_grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return user;
	}

	@Override
	public boolean addArea(String simple_name, String area_name) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "insert into t_area(simple_name,area_name) values(?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, simple_name);
			ps.setString(2, area_name);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean modArea(String simple_name, String area_name) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "update t_area set area_name=? WHERE simple_name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, area_name);
			ps.setString(2, simple_name);
			return ps.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean delArea(String simple_name) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "delete from t_area where simple_name =?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, simple_name);
			return ps.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean addClass(ClassObj obj) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "INSERT INTO t_class(class_name,b_area,b_course,start_time,class_number,simple_name) VALUES(?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, obj.getClass_name());
			ps.setString(2, obj.getB_area());
			ps.setString(3, obj.getB_course());
			ps.setString(4, obj.getStart_time());
			ps.setInt(5, obj.getClass_number());
			ps.setString(6, obj.getSimple_name());
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean modClass(ClassObj obj,String mark) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "UPDATE t_class SET class_name=?,b_area=?,b_course=?,start_time=?,class_number=?,simple_name=? WHERE simple_name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, obj.getClass_name());
			ps.setString(2, obj.getB_area());
			ps.setString(3, obj.getB_course());
			ps.setString(4, obj.getStart_time());
			ps.setInt(5, obj.getClass_number());
			ps.setString(6, obj.getSimple_name());
			ps.setString(7, mark);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean delClass(String simple_name) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "DELETE FROM t_class WHERE simple_name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, simple_name);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean addStartTime(String start_time) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "INSERT INTO t_class_start_time VALUES(?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, start_time);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean delStartTime(String start_time) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "DELETE FROM t_class_start_time WHERE start_time=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, start_time);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean addCourse(String simple_name, String course_name) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "INSERT INTO t_course(simple_name,course_name) VALUES(?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, simple_name);
			ps.setString(2, course_name);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean modCourse(String simple_name, String course_name, String mark) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "UPDATE t_course SET simple_name=?,course_name=? WHERE simple_name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, simple_name);
			ps.setString(2, course_name);
			ps.setString(3, mark);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean delCourse(String simple_name) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "DELETE FROM t_course WHERE simple_name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, simple_name);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean addExam(String exam_name, String exam_time, String course_id) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "INSERT INTO t_exam_list(exam_name,exam_time,course_id,state) VALUES(?,?,?,0)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, exam_name);
			ps.setString(2, exam_time);
			ps.setString(3, course_id);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean modExam(String exam_name, String exam_time,
			String course_id, String mark) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "UPDATE t_exam_list SET exam_name=?,exam_time=?,course_id=? WHERE exam_name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, exam_name);
			ps.setString(2, exam_time);
			ps.setString(3, course_id);
			ps.setString(4, mark);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean delExam(String mark) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "DELETE FROM t_exam_list WHERE exam_name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, mark); 
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public ArrayList<ExamBean> getExamList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "SELECT * from t_exam_list";
		ArrayList<ExamBean> exam_list = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ExamBean exam = new ExamBean();
				exam.setId(rs.getInt("id"));
				exam.setExam_name(rs.getString(I.Exam.EXAM_NAME));
				exam.setExam_time(rs.getString(I.Exam.EXAM_TIME));
				exam.setCourse_id(rs.getString(I.Exam.COURSE_ID)); 
				exam_list.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return exam_list;
	}

	@Override
	public boolean addExerciseGrade(int grade, String exe_time,
			String user_name, String course_id, int b_class,String start_time) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "INSERT INTO t_daily_exercise(grade,exe_time,user_name,course_id,b_class,b_start_time)"
				+ " VALUES(?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, grade); 
			ps.setString(2, exe_time);
			ps.setString(3, user_name);
			ps.setString(4, course_id);
			ps.setInt(5, b_class);
			ps.setString(6, start_time);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public ArrayList<ExerciseBean> getExercises(String user_name) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ExerciseBean> exerciseList = new ArrayList<>();
		String sql = "SELECT t_daily_exercise.grade,t_daily_exercise.exe_time,t_daily_exercise.user_name,"
				+ "t_course.course_name as course_id,t_class.class_name as b_class,t_daily_exercise.b_start_time "
				+ "as start_time from t_daily_exercise,t_class,t_course "
				+ "WHERE user_name=? AND t_daily_exercise.course_id=t_course.simple_name AND"
				+ " t_daily_exercise.b_class=t_class.id ORDER BY grade DESC LIMIT 0,10";
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, user_name);
			rs = ps.executeQuery();
			while(rs.next()){
				ExerciseBean exe = new ExerciseBean();
				exe.setGrade(rs.getInt(I.Exercise.GRADE));
				exe.setExe_tiem(rs.getString(I.Exercise.EXE_TIME));
				exe.setUser_name(rs.getString(I.Exercise.USER_NAME));
				exe.setCourse_id(rs.getString(I.Exercise.COURSE_ID));
				exe.setB_class(rs.getString(I.Exercise.B_CLASS));
				exe.setStart_time(rs.getString(I.Exercise.START_TIME));
				exerciseList.add(exe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return exerciseList;
	}

	@Override
	public int SortGradeinClass(int grade, int classNum) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "SELECT count(*) as count FROM t_daily_exercise WHERE grade>? AND b_class=?";
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, grade);
			ps.setInt(2, classNum);
			rs = ps.executeQuery();
			while(rs.next()){
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return count;
	}

	@Override
	public int SortGradeInCourse(int grade, String course_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "SELECT count(*) as count FROM t_daily_exercise WHERE grade>? AND course_id=?";
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, grade);
			ps.setString(2, course_id);
			rs = ps.executeQuery();
			while(rs.next()){
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return count;
	}

	@Override
	public int SortGradeInTime(int grade, String start_time) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "SELECT count(*) as count FROM t_daily_exercise WHERE grade>? AND b_start_time=?";
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, grade);
			ps.setString(2, start_time);
			rs = ps.executeQuery();
			while(rs.next()){
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return count;
	}

	@Override
	public ArrayList<ExerciseBean> getSortInClass(int classNum) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ExerciseBean> exerciseList = new ArrayList<>();
		String sql = "SELECT max(grade) as grade,exe_time,user_name,course_id,b_class,b_start_time as start_time "
				+ "FROM t_daily_exercise WHERE b_class=? GROUP BY user_name ORDER BY grade DESC ";
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, classNum);
			rs = ps.executeQuery();
			while(rs.next()){
				ExerciseBean exe = new ExerciseBean();
				exe.setGrade(rs.getInt(I.Exercise.GRADE));
				exe.setExe_tiem(rs.getString(I.Exercise.EXE_TIME));
				exe.setUser_name(rs.getString(I.Exercise.USER_NAME));
				exe.setCourse_id(rs.getString(I.Exercise.COURSE_ID));
				exe.setB_class(rs.getString(I.Exercise.B_CLASS));
				exe.setStart_time(rs.getString(I.Exercise.START_TIME));
				exerciseList.add(exe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return exerciseList;
	}

	@Override
	public ArrayList<ExerciseBean> getSortInTime(String time) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ExerciseBean> exerciseList = new ArrayList<>();
		String sql = "SELECT max(grade) as grade,exe_time,user_name,course_id,b_class,b_start_time as start_time "
				+ "FROM t_daily_exercise WHERE b_start_time=? "
				+ "GROUP BY user_name ORDER BY grade DESC ";
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, time);
			rs = ps.executeQuery();
			while(rs.next()){
				ExerciseBean exe = new ExerciseBean();
				exe.setGrade(rs.getInt(I.Exercise.GRADE));
				exe.setExe_tiem(rs.getString(I.Exercise.EXE_TIME));
				exe.setUser_name(rs.getString(I.Exercise.USER_NAME));
				exe.setCourse_id(rs.getString(I.Exercise.COURSE_ID));
				exe.setB_class(rs.getString(I.Exercise.B_CLASS));
				exe.setStart_time(rs.getString(I.Exercise.START_TIME));
				exerciseList.add(exe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return exerciseList;
	}

	@Override
	public ArrayList<ExerciseBean> getSortInCourse(String course_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ExerciseBean> exerciseList = new ArrayList<>();
		String sql = "SELECT max(grade) as grade,exe_time,user_name,course_id,b_class,b_start_time as start_time "
				+ "FROM t_daily_exercise WHERE course_id=? "
				+ "GROUP BY user_name ORDER BY grade DESC ";
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, course_id);
			rs = ps.executeQuery();
			while(rs.next()){
				ExerciseBean exe = new ExerciseBean();
				exe.setGrade(rs.getInt(I.Exercise.GRADE));
				exe.setExe_tiem(rs.getString(I.Exercise.EXE_TIME));
				exe.setUser_name(rs.getString(I.Exercise.USER_NAME));
				exe.setCourse_id(rs.getString(I.Exercise.COURSE_ID));
				exe.setB_class(rs.getString(I.Exercise.B_CLASS));
				exe.setStart_time(rs.getString(I.Exercise.START_TIME));
				exerciseList.add(exe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return exerciseList;
	}

	@Override
	public ArrayList<ExamBean> getExamNow() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "SELECT * from t_exam_list where state =1";
		ArrayList<ExamBean> exam_list = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ExamBean exam = new ExamBean();
				exam.setId(rs.getInt("id"));
				exam.setExam_name(rs.getString(I.Exam.EXAM_NAME));
				exam.setExam_time(rs.getString(I.Exam.EXAM_TIME));
				exam.setCourse_id(rs.getString(I.Exam.COURSE_ID)); 
				exam_list.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return exam_list;
	}

	@Override
	public boolean modExamState(String exam_name, Integer exam_state) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "UPDATE t_exam_list SET state=? WHERE exam_name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, exam_state);
			ps.setString(2, exam_name);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean examNowById(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "SELECT * from t_exam_list where state =1 and exam_name=?";
		String nameSql = "SELECT exam_name from t_exam_list where id =?";
		String exam_name = getExamName(nameSql,id);
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, exam_name);
			rs = ps.executeQuery();
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return false;
	}

	private String getExamName(String nameSql, int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(nameSql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				return rs.getString("exam_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return null;
	}

	@Override
	public boolean addExamGrade(int exam_id, int grade, String time,
			String user_name, int b_class, String course_id,String b_start_time) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "INSERT INTO t_exam_grade(exam_id,grade,submit_time,user_name,b_class,course_id,b_start_time)"
				+ " VALUES(?,?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, exam_id); 
			ps.setInt(2, grade);
			ps.setString(3, time);
			ps.setString(4, user_name);
			ps.setInt(5, b_class);
			ps.setString(6, course_id);
			ps.setString(7, b_start_time);
			return ps.executeUpdate()==1; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public ArrayList<ClassExamGradeBean> getExamGrade(int exam_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "SELECT t_exam_grade.user_name,max(t_exam_grade.grade) as grade,t_exam_grade.submit_time,t_class.class_name "
				+ "FROM t_exam_grade,t_class "
				+ "WHERE t_exam_grade.exam_id=? "
				+ "AND t_exam_grade.b_class=t_class.id GROUP BY user_name ORDER BY grade DESC";
		ArrayList<ClassExamGradeBean> grade_list = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, exam_id);
			rs = ps.executeQuery();
			while(rs.next()){
				ClassExamGradeBean exam = new ClassExamGradeBean();
				exam.setUser_name(rs.getString(I.ClassExamGrade.USER_NAME));
				exam.setGrade(rs.getInt(I.ClassExamGrade.GRADE));
				exam.setSubmit_time(rs.getString(I.ClassExamGrade.SUBMIT_TIME));
				exam.setClass_name(rs.getString(I.ClassExamGrade.CLASS_NAME));
				grade_list.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return grade_list;
	}

	@Override
	public ArrayList<ExamGradeAvgBean> getAvgExamGrade(int exam_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "SELECT avg(t_exam_grade.grade) as grade,t_class.class_name "
				+ "FROM t_exam_grade,t_class "
				+ "WHERE t_exam_grade.exam_id=? AND t_exam_grade.b_class=t_class.id "
				+ "GROUP BY class_name ORDER BY grade DESC";
		ArrayList<ExamGradeAvgBean> grade_list = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, exam_id);
			rs = ps.executeQuery();
			while(rs.next()){
				ExamGradeAvgBean exam = new ExamGradeAvgBean();
				exam.setAvgGrade(rs.getFloat(I.ClassExamGrade.GRADE));
				exam.setClass_name(rs.getString(I.ClassExamGrade.CLASS_NAME));
				grade_list.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return grade_list;
	}

	@Override
	public ArrayList<ExamClassGradeBean> getClassGradeList(int exam_id,
			int b_class) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "SELECT user_name,grade FROM t_exam_grade WHERE exam_id=? AND b_class=? ORDER BY grade DESC";
		ArrayList<ExamClassGradeBean> grade_list = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, exam_id);
			ps.setInt(2, b_class);
			rs = ps.executeQuery();
			while(rs.next()){
				ExamClassGradeBean exam = new ExamClassGradeBean();
				exam.setUser_name(rs.getString(I.ExamGrade.USER_NAME));
				exam.setGrade(rs.getInt(I.ExamGrade.GRADE));
				grade_list.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return grade_list;
	}

	@Override
	public String getClassNameById(int classId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DBUtil.getCon();
		String sql = "SELECT class_name from t_class WHERE id=?";
		String className = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, classId);
			rs = ps.executeQuery();
			while(rs.next()){
				className = rs.getString("class_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return className;
	}

	@Override
	public ArrayList<ExerciseBean> getNearlyExercises(String userName) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ExerciseBean> exerciseList = new ArrayList<>();
		String sql = "SELECT t_daily_exercise.grade,t_daily_exercise.exe_time,t_daily_exercise.user_name,"
				+ "t_course.course_name as course_id,t_class.class_name as b_class,t_daily_exercise.b_start_time "
				+ "as start_time from t_daily_exercise,t_class,t_course "
				+ "WHERE user_name=? AND t_daily_exercise.course_id=t_course.simple_name AND"
				+ " t_daily_exercise.b_class=t_class.id ORDER BY exe_time DESC LIMIT 0,10";
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			while(rs.next()){
				ExerciseBean exe = new ExerciseBean();
				exe.setGrade(rs.getInt(I.Exercise.GRADE));
				exe.setExe_tiem(rs.getString(I.Exercise.EXE_TIME));
				exe.setUser_name(rs.getString(I.Exercise.USER_NAME));
				exe.setCourse_id(rs.getString(I.Exercise.COURSE_ID));
				exe.setB_class(rs.getString(I.Exercise.B_CLASS));
				exe.setStart_time(rs.getString(I.Exercise.START_TIME));
				exerciseList.add(exe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return exerciseList;
	}

	@Override
	public ArrayList<WordContentBean> getWordContent() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<WordContentBean> exerciseList = new ArrayList<>();
		String sql = "SELECT * from t_words_content";
		con = DBUtil.getCon();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				WordContentBean exe = new WordContentBean();
				exe.setId(rs.getInt(I.WordContent.ID));
				exe.setWord(rs.getString(I.WordContent.WORD));
				exe.setCourse_id(rs.getString(I.WordContent.COURSE_ID));
				exerciseList.add(exe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, rs);
		}
		return exerciseList;
	}

	@Override
	public boolean updateUser(String uid, String pwd, String user_name) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "UPDATE t_user SET pwd=?,user_name=? WHERE uid=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, pwd);
			ps.setString(2, user_name);
			ps.setString(3,uid);
			return ps.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

	@Override
	public boolean uploadBestGrade(String uid, int grade) {
		Connection con = null;
		PreparedStatement ps = null;
		con = DBUtil.getCon();
		String sql = "UPDATE t_user SET top_grade=? WHERE uid=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, grade);
			ps.setString(2,uid);
			return ps.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeCon(con, ps, null);
		}
		return false;
	}

}                                                                                                                                                                                                                                                                                                                                                                 
