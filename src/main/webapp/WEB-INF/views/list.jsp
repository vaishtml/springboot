<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Academy Management System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f0f2f5;
            color: #333;
        }

        /* ---- Header ---- */
        header {
            background: linear-gradient(135deg, #1a237e, #283593);
            color: white;
            padding: 18px 40px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            box-shadow: 0 2px 8px rgba(0,0,0,0.3);
        }
        header h1 { font-size: 1.6rem; letter-spacing: 1px; }
        header span { font-size: 0.9rem; opacity: 0.75; }

        /* ---- Navbar ---- */
        nav {
            background: #283593;
            display: flex;
            gap: 4px;
            padding: 0 40px;
        }
        nav a {
            color: rgba(255,255,255,0.8);
            text-decoration: none;
            padding: 12px 20px;
            font-size: 0.9rem;
            font-weight: 600;
            transition: background 0.2s, color 0.2s;
            border-bottom: 3px solid transparent;
        }
        nav a:hover { color: white; background: rgba(255,255,255,0.1); }
        nav a.active { color: white; border-bottom: 3px solid #ffd740; }

        /* ---- Main Container ---- */
        .container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 0 20px;
        }

        /* ---- Toolbar ---- */
        .toolbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .toolbar h2 { font-size: 1.3rem; color: #1a237e; }

        .btn-add {
            background: #1a237e;
            color: white;
            padding: 10px 22px;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-size: 0.95rem;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
        }
        .btn-add:hover { background: #283593; }

        /* ---- Alert Messages ---- */
        .alert {
            padding: 12px 18px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 0.95rem;
        }
        .alert-success { background: #e8f5e9; color: #2e7d32; border-left: 4px solid #4caf50; }
        .alert-error   { background: #ffebee; color: #c62828; border-left: 4px solid #ef5350; }

        /* ---- Table ---- */
        .card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            overflow: hidden;
        }
        table { width: 100%; border-collapse: collapse; }
        thead tr { background: #1a237e; color: white; }
        thead th {
            padding: 14px 18px;
            text-align: left;
            font-size: 0.9rem;
            letter-spacing: 0.5px;
            text-transform: uppercase;
        }
        tbody tr {
            border-bottom: 1px solid #e8eaf6;
            transition: background 0.15s;
        }
        tbody tr:last-child { border-bottom: none; }
        tbody tr:hover { background: #e8eaf6; }
        tbody td { padding: 13px 18px; font-size: 0.95rem; }

        /* ---- Description Badge ---- */
        .badge {
            display: inline-block;
            padding: 3px 10px;
            border-radius: 12px;
            font-size: 0.8rem;
            font-weight: 600;
        }
        .badge-beginner  { background: #e8f5e9; color: #1b5e20; }
        .badge-intermediate  { background: #fff8e1; color: #e65100; }
        .badge-advanced   { background: #fbe9e7; color: #bf360c; }
        .badge-default  { background: #eceff1; color: #455a64; }

        /* ---- Action Buttons ---- */
        .btn-edit {
            background: #ff8f00;
            color: white;
            padding: 6px 12px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 0.82rem;
            font-weight: 600;
            transition: background 0.2s;
            margin-right: 5px;
        }
        .btn-edit:hover { background: #e65100; }

        .btn-delete {
            background: #c62828;
            color: white;
            padding: 6px 12px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 0.82rem;
            font-weight: 600;
            transition: background 0.2s;
            cursor: pointer;
            border: none;
        }
        .btn-delete:hover { background: #b71c1c; }

        /* ---- Empty State ---- */
        .empty-state {
            text-align: center;
            padding: 50px 20px;
            color: #888;
        }
        .empty-state p { font-size: 1.1rem; margin-bottom: 15px; }

        /* ---- Footer ---- */
        footer {
            text-align: center;
            margin-top: 40px;
            margin-bottom: 20px;
            color: #aaa;
            font-size: 0.85rem;
        }
    </style>
</head>
<body>

<header>
    <h1>🎓 Academy Management System</h1>
    <span>Spring Boot | JPA | H2</span>
</header>

<nav>
    <a href="/" class="active">📚 Courses</a>
    <a href="/students">🧑‍🎓 Students</a>
</nav>

<div class="container">

    <!-- Success / Error Messages -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">${errorMessage}</div>
    </c:if>

    <div class="toolbar" style="flex-wrap: wrap; gap: 15px;">
        <div>
            <h2>${pageTitle}</h2>
        </div>
        
        <div style="display: flex; gap: 10px; align-items: center; flex-grow: 1; justify-content: center;">
            <form action="/" method="get" style="display: flex; gap: 8px;">
                <input type="text" name="search" value="${searchKeyword}" placeholder="Search titles..." 
                       style="padding: 8px 15px; border: 1px solid #ddd; border-radius: 6px; font-size: 0.9rem; width: 250px;">
                <button type="submit" class="btn-add" style="padding: 8px 15px;">Search</button>
            </form>
            
            <form action="/" method="get" style="display: flex; gap: 8px; align-items: center;">
                <span style="font-size: 0.9rem; color: #666;">Level:</span>
                <select name="description" onchange="this.form.submit()" 
                        style="padding: 8px 10px; border: 1px solid #ddd; border-radius: 6px; font-size: 0.9rem;">
                    <option value="All" ${selectedDescription eq 'All' ? 'selected' : ''}>All Levels</option>
                    <option value="Beginner" ${selectedDescription eq 'Beginner' ? 'selected' : ''}>Beginner</option>
                    <option value="Intermediate" ${selectedDescription eq 'Intermediate' ? 'selected' : ''}>Intermediate</option>
                    <option value="Advanced" ${selectedDescription eq 'Advanced' ? 'selected' : ''}>Advanced</option>
                </select>
            </form>
            
            <c:if test="${not empty searchKeyword or (not empty selectedDescription and selectedDescription ne 'All')}">
                <a href="/" style="font-size: 0.85rem; color: #c62828; text-decoration: none; font-weight: 600;">Clear All</a>
            </c:if>
        </div>

        <a href="/add" class="btn-add">+ Add New Course</a>
    </div>

    <div class="card">
        <c:choose>
            <c:when test="${not empty courses}">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Student</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="course" items="${courses}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td><strong>${course.title}</strong></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${course.description eq 'Beginner'}">
                                            <span class="badge badge-beginner">${course.description}</span>
                                        </c:when>
                                        <c:when test="${course.description eq 'Intermediate'}">
                                            <span class="badge badge-intermediate">${course.description}</span>
                                        </c:when>
                                        <c:when test="${course.description eq 'Advanced'}">
                                            <span class="badge badge-advanced">${course.description}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-default">${course.description}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${course.student.name}</td>
                                <td>
                                    <a href="/edit/${course.id}" class="btn-edit">&#9998; Edit</a>
                                    <a href="/delete/${course.id}"
                                       class="btn-delete"
                                       onclick="return confirm('Are you sure you want to delete this course?');">
                                        &#128465; Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <p>No courses found in the academy.</p>
                    <a href="/add" class="btn-add">+ Add Your First Course</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</div>

<footer>
    &copy; 2024 Academy Management System &mdash; Built with Spring Boot &amp; JSP
</footer>

</body>
</html>
