/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stek.servise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class Employee extends HttpServlet {

    
    // JDBC driver name and database URL
    //static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    //static final String DB_URL = "jdbc:jtds:sqlserver://obl-devel:1433/hrstek;characterEncoding=UTF-8";
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
         Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

        try {

            String task = request.getParameter("task");
            task = (task == null ? "null" : task);

            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/hrstek");
            conn = ds.getConnection();
            stmt = conn.createStatement();
            String sql;



            if (task.equals("COMMUNIC")) {
                String orgId = request.getParameter("org_id");           
                String lastName = request.getParameter("last_name");
                String jobName = request.getParameter("job_name");
                String telAts = request.getParameter("tel_ats").replaceAll("-", "");
                StringBuilder whereQuery = new StringBuilder(" where 1=1 ");
                if  (!isEmpty(orgId)){
                    whereQuery.append(" and job = '" + orgId + "'");
                }
                if  (!isEmpty(lastName)){
                    whereQuery.append(" and fname LIKE '" + lastName + "%'");
                }
                if  (!isEmpty(jobName)){
                    whereQuery.append(" and posada = '" + jobName + "'");
                }
                if  (!isEmpty(telAts) && !"*".equals(telAts)){
                    whereQuery.append(" and (replace(tel1,'-','') like '%" + telAts + "%' or replace(tel2,'-','') like '%" + telAts + "%')");
                }
                
//                

                String value = new String();
                sql = "SELECT tab_no, "
                        + "fname+' '+sname+' '+tname as name, "
                        + "posada,"
                        + "isnull(tel1,'')as tel1,isnull(tel2,'')as tel2,isnull(email,'') as email,"
                        + "job,name as job_txt  FROM emploe left join tree on job = tree.id " + whereQuery;
                        
                rs = stmt.executeQuery(sql);


                while (rs.next()) {
                    String name = rs.getString("name");
                    String tel1 = rs.getString("tel1");
                    String tel2 = rs.getString("tel2");
                    String jobtxt = rs.getString("posada");
                    String em = rs.getString("email");
                    String email = "<a href='mailto:" + em + "'>" + em + "</a>";
                    String tn = rs.getString("tab_no");
                    String org = rs.getString("job_txt");
                    String org_id = rs.getString("job");
                    value += "<tr class='row'><td>"
                            + "<div id='" + org_id + "' style='display:none;'><div id='dlg_cont_m'>"
                            + "<h3>" + name + "</h3>"
                            + "<h4>" + org + "</h4>" + "<h4>" + jobtxt + "</h4><h4 class='a1_noed' >" + email + "</h4><input class='a1_ed' size='40' name='email' value='" + em + "' >"
                            + "<a href='#' class='bada'>Повідомити про невідповідність адреси</a><br />"
                            + "</div>"
                            + "<div id='dlg_cont_t'>"
                            + "<h3 class='a2_noed'>" + tel1 + "</h3><span class='a2_ed'>()<input size='5' name='tel_ats' value='" + tel1 + "' ></span><br  class='ed' />"
                            + "<h4  class='a2_noed'>міськ. " + tel2 + "</h4>"
                            + "<span class='a2_ed'>міськ.()<input size='5' name='tel_MISTO' value='" + tel2 + "' ></span><br  class='a2_ed' />"
                            + "<span class='ed' ><button class='save' onclick='savefunck()'>Зберегти</button></span>"
                            + "<a href='#' class='badt'>Повідомити про невідповідність телефону</a>"
                            + "</div>"
                            + "<div id='dlg_cont_f'><div style='display:none;'>" + tn + "</div>"
                            + "<img src='http://obl-05/data/prvidom/foto/стек/f" + tn + "f.jpg'>"
                            + "</div></div>"
                            + name + "</td><td>" + tel1 + "</td><td>" + email + "</td><td>" + jobtxt + "</td></tr>";
                }
                out.println(value);
            } else if (task.equals("JOB_POS")) {
                response.setContentType("text/xml;charset=UTF-8");
                String value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><main>";

                sql = "Select posada  FROM emploe  group by posada";
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    value += "<LONG_TEXT>" + rs.getString("posada") + "</LONG_TEXT>";
                }
                value += "</main>";
                out.println(value);
            } else if (task.equals("ORG_UNIT_LIST")) {

                sql = "SELECT id,name,pdrent_id FROM tree";
                rs = stmt.executeQuery(sql);
                String value = new String();

                ArrayList<Item> arr = new ArrayList<Item>();
                while (rs.next()) {
                    arr.add(new Item(rs.getString("id"), rs.getString("name"), rs.getString("pdrent_id")));
                }
                value += recurs(3, arr, "0");

                out.println(value);
            } else {
                out.println("Employee: uncorrect task = " + task);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Create Sales Order servlet";


    }

    private String getParam(String strName, String strDefault, HttpServletRequest request) {
        String strValue = request.getParameter(strName);
        if (strValue == null) {
            return strDefault;
        }
        return strValue;
    }

    private String recurs(int i, ArrayList<Item> arr, String id) throws SQLException {

        String value = new String();
        String rez = new String();
        if (i == 1) {
            return "";
        }

        ArrayList<Item> arrcopy = null;

        for (Item rs : arr) {
            if (rs.par_id.equals(id)) {
                value += "<li><span id='sp_" + rs.id + "'>" + rs.name+"</span>";
                String idL1 = rs.id;
                value += recurs(i - 1, arr, idL1);
                value += "</li>";
            }
        }
        if (value.length() > 0) {
            rez = "<ul>" + value + "</ul>";
        } else {
            rez = value;
        }

        return rez;
    }
    private static final char[] hexChar = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    private static String unicodeEscape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >> 7) > 0) {
                sb.append("\\u");
                sb.append(hexChar[(c >> 12) & 0xF]); // append the hex character for the left-most 4-bits
                sb.append(hexChar[(c >> 8) & 0xF]);  // hex for the second group of 4-bits from the left
                sb.append(hexChar[(c >> 4) & 0xF]);  // hex for the third group
                sb.append(hexChar[c & 0xF]);         // hex for the last group, e.g., the right most 4-bits
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
private boolean isEmpty(String string){
    return string == null || "".equals(string);
}
    class Item {

        public String id;
        public String name;
        public String par_id;

        public Item(String id, String name, String par_id) {
            this.id = id;
            this.name = name;
            this.par_id = par_id;
        }
    }
}