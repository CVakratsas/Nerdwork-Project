package application.functinonality;

public class Student {
	private String id;
	private String name;
	private int semester;
	private String orientation;
	
	public Student(String id, String name, int semester, String orientation) {
		this.id = id;
		this.name = name;
		this.semester = semester;
		this.orientation = orientation;
	}
	
	public String toString() {
		String str = "Student: " + name + " (" + id + ")\n"
				+ "Semester: " + semester + "\n" + orientation;
		return str;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
}