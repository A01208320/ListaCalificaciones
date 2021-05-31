%materia('Nombre de la Materia', Codigo_de_la_Materia).
:-include('Materias.pl').

%alumno('Nombre del Alumno', 'Grupo', Numero_de_Lista).
:-include('Alumnos.pl').

%calificacion(Periodo, Codigo_de_la_Materia, 'Grupo', Numero_de_Lista, Calificacion).
:-include('Calificaciones.pl').

%grupo('Grupo').
:-include('Grupos.pl').

%periodo(Periodo).
:-include('Periodos.pl').

%listaMaterias(X).
% Regresa la informacion de una Materia por respuesta
listaMaterias([M, C]):-
    materia(M, C).

%listaAlumnos('Grupo', X).
% Regresa una lista con la informacion de un alumno por respuesta
listaAlumnos(Gru, [NomA, Gru, NumL]):-
    alumno(NomA, Gru, NumL),
    NomA \= '-1'.
listaAlumnosAll(Gru, [NomA, Gru, NumL]):-
    alumno(NomA, Gru, NumL).

%getCalif(Periodo, 'Nombre de la Materia', 'Grupo', X).
% Returns a list of all grades
getCalif(P, Mat, Gru, [P, Mat, Gru, NumL, NomA, Cal]):-
    materia(Mat, CodM),
    alumno(NomA, Gru, NumL),
    calificacion(P, CodM, Gru, NumL, Cal),
    Cal>0.
getCalifAll(P, Mat, Gru, [P, Mat, Gru, NumL, NomA, Cal]):-
    materia(Mat, CodM),
    alumno(NomA, Gru, NumL),
    calificacion(P, CodM, Gru, NumL, Cal).

%getGrupos(X).
% Returns all groups
getGrupos([X]):-
    grupo(X).

%getPeriods(X).
%Returns all periods
getPeriods([X]):-
    periodo(X).