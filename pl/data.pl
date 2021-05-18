%materia('Nombre de la Materia', Codigo_de_la_Materia).
:-include('Materias.pl').

%alumno('Nombre del Alumno', Numero_de_Lista, Grado, Grupo).
:-include('Alumnos.pl').

%calificacion(Periodo, Codigo_de_la_Materia, Grado, Grupo, Numero_de_Lista, Calificacion).
:-include('Calificaciones.pl').

%listaMaterias(X).
% Regresa una lista de Materia y Codigo
listaMaterias([M, C]):-
    materia(M, C).

%listaAlumnos(Grado, Grupo, X).
% Regresa una lista con la informacion de un alumno
listaAlumnos(Gra, Gru, [Gra, Gru, NumL, NomA]):-
    alumno(NomA, NumL, Gra, Gru).

%getCalif(Periodo, Nombre de la Materia, Grado, Grupo, R).
% Returns a list of all grades
getCalif(P, Mat, Gra, Gru, [P, Gra, Gru, Mat, NomA, Cal]):-
    materia(Mat, CodM),
    alumno(NomA, NumL, Gra, Gru),
    calificacion(P, CodM, Gra, Gru, NumL, Cal).