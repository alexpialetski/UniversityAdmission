package by.epam.pialetskialiaksei;

public final class Path {
        public static final String LOGIN_PAGE = "login.jsp";
        public static final String REDIRECT_LOGIN_PAGE = "controller?command=viewLogin";

        public static final String WELCOME_PAGE = "welcome.jsp";

        public static final String ERROR_PAGE = "/WEB-INF/view/errorPage.jsp";

        public static final String FORWARD_SCORE_PAGE = "/WEB-INF/view/report/score.jsp";

        public static final String FORWARD_RESULT_PAGE = "/WEB-INF/view/report/result.jsp";

        public static final String FORWARD_CLIENT_REGISTRATION_PAGE = "/WEB-INF/view/client/register.jsp";
        public static final String REDIRECT_CLIENT_REGISTRATION_PAGE = "controller?command=view_registration";

        public static final String REDIRECT_TO_PROFILE = "controller?command=viewProfile";
        public static final String FORWARD_ADMIN_PROFILE = "/WEB-INF/view/admin/profile/view.jsp";
        public static final String FORWARD_CLIENT_PROFILE = "/WEB-INF/view/client/profile/view.jsp";

        public static final String REDIRECT_TO_FACULTY = "controller?command=viewFaculty&facultyId=?";

        public static final String REDIRECT_TO_VIEW_ALL_SUBJECTS = "controller?command=viewAllSubjects";

        public static final String FORWARD_FACULTY_VIEW_ALL_CLIENT = "/WEB-INF/view/faculty/faculties.jsp";

        public static final String FORWARD_FACULTY_VIEW_ADMIN = "/WEB-INF/view/admin/faculty/view.jsp";

        public static final String FORWARD_SUBJECT_VIEW_ALL_ADMIN = "/WEB-INF/view/admin/subject/list.jsp";
    }
