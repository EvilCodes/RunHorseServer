package cn.lyj.hero.entity;

public class ExamBean {
	private int id;
	private String exam_name;
	private String exam_time;
	private String course_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExam_name() {
		return exam_name;
	}
	public void setExam_name(String exam_name) {
		this.exam_name = exam_name;
	}
	public String getExam_time() {
		return exam_time;
	}
	public void setExam_time(String exam_time) {
		this.exam_time = exam_time;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	@Override
	public String toString() {
		return "ExamBean [id=" + id + ", exam_name=" + exam_name
				+ ", exam_time=" + exam_time + ", course_id=" + course_id + "]";
	}
	public ExamBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExamBean(int id, String exam_name, String exam_time, String course_id) {
		super();
		this.id = id;
		this.exam_name = exam_name;
		this.exam_time = exam_time;
		this.course_id = course_id;
	}
	
}
