<%-- 
    Document   : index
    Created on : 2 лист 2012, 11:11:19
    Author     : AsuSV
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
             


        <hr>
       
        <table>
            <tr>                
                <td>
                    <h3>Personal Communication List</h3>
                    <FORM ACTION="Employee" METHOD="post">
                        <INPUT TYPE="hidden" value="COMMUNIC" NAME="task">
                        Person: <INPUT TYPE="text" NAME="employee_id"><br/>
                        Last name: <INPUT TYPE="text" NAME="last_name"><br/>
                        First name: <INPUT TYPE="text" NAME="first_name"><br/>
                        Org name: <INPUT TYPE="text" NAME="org_name"><br/>
                        Job name: <INPUT TYPE="text" NAME="job_name"><br/>
                        Org Id(todo): <INPUT TYPE="text" NAME="org_id"><br/>
                        Job Id(todo): <INPUT TYPE="text" NAME="job_id"><br/>
                        <INPUT TYPE="submit" VALUE="Search">
                    </FORM>
                </td>
                <td>
                    <h3>Personal Communication Full List</h3>
                    <FORM ACTION="Employee" METHOD="post">
                        <INPUT TYPE="hidden" value="COMMUNIC" NAME="task">
                        Only Communic: <INPUT TYPE="text" NAME="only_communic"><br/>
                        Person: <INPUT TYPE="text" NAME="employee_id"><br/>
                        Last name: <INPUT TYPE="text" NAME="last_name"><br/>
                        First name: <INPUT TYPE="text" NAME="first_name"><br/>
                        Org name: <INPUT TYPE="text" NAME="org_name"><br/>
                        Job name: <INPUT TYPE="text" NAME="job_name"><br/>
                        Org Id(todo): <INPUT TYPE="text" NAME="org_id"><br/>
                        Job Id(todo): <INPUT TYPE="text" NAME="job_id"><br/>
                        TEL_ATS: <INPUT TYPE="text" NAME="tel_ats"><br/>
                        <INPUT TYPE="submit" VALUE="Search">
                    </FORM>
                </td>
                <td>
                    <h3>Personal Communication Update</h3>
                    <FORM ACTION="Employee" METHOD="post">
                        <INPUT TYPE="hidden" value="COMMUNIC_UPDATE" NAME="task">
                        Person: <INPUT TYPE="text" NAME="employee_id"><br/>
                        "ats": <INPUT TYPE="text" NAME="ats"><br/>
                        "misto": <INPUT TYPE="text" NAME="misto"><br/>
                        "mob1": <INPUT TYPE="text" NAME="mob1"><br/>
                        "mob2": <INPUT TYPE="text" NAME="mob2"><br/>
                        "home": <INPUT TYPE="text" NAME="home"><br/>
                        "email": <INPUT TYPE="text" NAME="email"><br/>
                        <INPUT TYPE="submit" VALUE="Update">
                    </FORM>
                </td>
                <td>
                    <h3>Job Position List</h3>
                    <FORM ACTION="Employee" METHOD="post">
                        <INPUT TYPE="hidden" value="JOB_POS" NAME="task">                        
                        <INPUT TYPE="submit" VALUE="Search">
                    </FORM>
                </td>
                <td>
                    <h3>Organization Units List</h3>
                    <FORM ACTION="Employee" METHOD="post">
                        <INPUT TYPE="hidden" value="ORG_UNIT_LIST" NAME="task">
                        <INPUT TYPE="submit" VALUE="Search">
                    </FORM>
                </td>                
            </tr>                        
        </table>
    </body>
</html>
