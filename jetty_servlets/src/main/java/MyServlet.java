import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;

@WebServlet("/Servlet")
public class MyServlet extends HttpServlet {

    private PostgresConnection pconnect;
    private QuaeryGenerator generator;


    public MyServlet(PostgresConnection pconnect, QuaeryGenerator generator) {
        this.pconnect = pconnect;
        this.generator = generator;
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> cond = new RequestObjectReader(request).readObject(generator.getFields());
        ResultSet resultSet;
        if(cond == null)
            resultSet = pconnect.executeQuaery(generator.findAll());
        else
            resultSet = pconnect.executeQuaery(generator.find(cond));

        response.getWriter().println("found:");
        response.getWriter().println(ToStringHelper.resultSet(resultSet));
    }
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> cond = new RequestObjectReader(request).readObject(generator.getFields());
        if(cond == null) {
            response.getWriter().println("Obj must be not null");
        } else {
            pconnect.execute(generator.insert(cond));
            response.getWriter().println("DONE");
        }
    }
    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> cond = new RequestObjectReader(request).readObject(generator.getFields());
        if(cond == null) {
            response.getWriter().println("Obj must be not null");
        } else {
            pconnect.execute(generator.delete(cond));
            response.getWriter().println("DONE");
        }
    }
    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> cond = new RequestObjectReader(request).readObject(generator.getFields());
        Map<String, Object> set = new RequestObjectReader(request).readObject(generator.getFields(), "SET");
        if(cond == null || set == null) {
            response.getWriter().println("Obj must be not null");
        } else {
            pconnect.execute(generator.update(cond, set));
            response.getWriter().println("DONE");
        }
    }
}