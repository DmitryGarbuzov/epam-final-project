package com.garbuzov.diary.command;

import java.util.EnumMap;

public class CommandMap {

    private static EnumMap<CommandType, Command> enumMap = new EnumMap<>(CommandType.class);
    private static CommandMap instance = new CommandMap();

    static {
        enumMap.put(CommandType.SIGN_UP, new SignUpCommand());
        enumMap.put(CommandType.PARENT_REGISTRATION, new ParentRegistrationCommand());
        enumMap.put(CommandType.SUBJECT_ADD, new SubjectAddCommand());
        enumMap.put(CommandType.GRADE_ADD, new GradeAddCommand());
        enumMap.put(CommandType.FIND_ALL_GRADE, new FindAllGradeCommand());
        enumMap.put(CommandType.STUDENT_ADD, new StudentAddCommand());
        enumMap.put(CommandType.FIND_ALL_STUDENT, new FindAllStudentCommand());
        enumMap.put(CommandType.FIND_ALL_SUBJECT, new FindAllSubjectCommand());
        enumMap.put(CommandType.TEACHER_REGISTRATION, new TeacherRegistrationCommand());
        enumMap.put(CommandType.FIND_ALL_PARENT, new FindAllParentCommand());
        enumMap.put(CommandType.FIND_ALL_TEACHER, new FindAllTeacherCommand());
        enumMap.put(CommandType.PARENT_DELETION, new ParentDeletionCommand());
        enumMap.put(CommandType.TEACHER_DELETION, new TeacherDeletionCommand());
        enumMap.put(CommandType.STUDENT_UPDATE, new StudentUpdateCommand());
        enumMap.put(CommandType.GRADE_UPDATE, new GradeUpdateCommand());
        enumMap.put(CommandType.SUBJECT_UPDATE, new SubjectUpdateCommand());
        enumMap.put(CommandType.LANGUAGE_SWITCH, new LanguageSwitchCommand());
        enumMap.put(CommandType.GRADE_FOR_TEACHER, new GradeForTeacherCommand());
        enumMap.put(CommandType.LESSON_ADD, new LessonAddCommand());
        enumMap.put(CommandType.LESSON_DELETION, new LessonDeletionCommand());
        enumMap.put(CommandType.FIND_JOURNAL, new FindJournalCommand());
        enumMap.put(CommandType.TIMETABLE_ADD, new TimetableAddCommand());
    }

    private CommandMap() {}

    public static CommandMap getInstance() {
        return instance;
    }

    public Command get(String command) {
        CommandType type = CommandType.valueOf(CommandType.class, command.toUpperCase());
        return enumMap.get(type);
    }
}
