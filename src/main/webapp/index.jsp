<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menú Principal</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        h1 {
            text-align: center;
        }
        .menu {
            width: 300px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
        }
        .menu a {
            display: block;
            margin: 10px 0;
            padding: 10px;
            text-align: center;
            background-color: #f4f4f4;
            text-decoration: none;
            color: #333;
            border-radius: 5px;
        }
        .menu a:hover {
            background-color: #e4e4e4;
        }
    </style>
</head>
<body>
    <h1>Menú Principal</h1>
    <div class="menu">
        <a href="JSP/verAsignacion.jsp?menu=verAsignacion">Ver Asignación</a>
        <a href="JSP/actualizarAsignaciones.jsp?menu=actualizarAsignaciones">Actualizar Asignación</a>
        <a href="JSP/eliminarAsignaciones.jsp?menu=eliminarAsignaciones">Eliminar Asignación</a>
        <a href="JSP/insertarAsignacion.jsp?menu=insertarAsignacion">Insertar Asignación</a>
        <a href="JSP/listarAsignaciones.jsp?menu=listarAsignaciones">Listar Asignaciones</a>
    </div>
</body>
</html>
