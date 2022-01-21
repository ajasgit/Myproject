package QueryDetails;

public class ProjectQuery {

	public static final String ADD_PROJECT = "insert into project_model (id, f_date,p_name,s_date)"+ "values(?,?,?,?)";
	public static final String FIND_ALL = "SELECT * from project_model ";
	public static final String FIND_PROJECT_BYNAME = "SELECT * from project_model WHERE p_name=?";
	public static final String FIND_HIGHEST_ID = "SELECT * from project_model where id=(select max(id) from project_model) ";

}
