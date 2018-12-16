/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.*;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import example.Computer;
import example.*;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

    public static ActorThreadPool actorThreadPool;
    private static Example example;

    /**
     * Begin the simulation. Should not be called before attachActorThreadPool()
     */
    public static void start() {
        actorThreadPool.start();
        Warehouse warehouse = new Warehouse();
        for (Computer l : example.getComputers()) {
            bgu.spl.a2.sim.Computer computer = new bgu.spl.a2.sim.Computer(l.getType());
            Long sig = Long.parseLong(l.getSigFail());
            computer.setFailSig(sig);
            sig = Long.parseLong(l.getSigSuccess());
            computer.setSuccessSig(sig);
            warehouse.addComputer(computer);
        }

        CountDownLatch countDownLatch = new CountDownLatch(example.getPhase1().size());
        for (Phase1 p1 : example.getPhase1()) {

            switch (p1.getAction()) {
                case "Add Student": {

                    AddStudent addStudent = new AddStudent(p1.getDepartment(), p1.getStudent());
                    addStudent.getResult().subscribe(() -> countDownLatch.countDown());
                    actorThreadPool.submit(addStudent, p1.getDepartment(), new DepartmentPrivateState());
                    actorThreadPool.getPrivateState(p1.getDepartment()).addRecord(p1.getAction());
                    break;
                }
                case "Open Course": {
                    OpenANewCourse openANewCourse = new OpenANewCourse(p1.getDepartment(), p1.getCourse(), p1.getSpace(), p1.getPrerequisites());
                    openANewCourse.getResult().subscribe(() -> countDownLatch.countDown());
                    actorThreadPool.submit(openANewCourse, p1.getDepartment(), new DepartmentPrivateState());
                    actorThreadPool.getPrivateState(p1.getDepartment()).addRecord(p1.getAction());
                    break;
                }
                case "Close Course": {
                    CloseACourse closeACourse = new CloseACourse(p1.getDepartment(), p1.getCourse());
                    closeACourse.getResult().subscribe(() -> countDownLatch.countDown());
                    actorThreadPool.submit(closeACourse, p1.getDepartment(), null);
                    actorThreadPool.getPrivateState(p1.getDepartment()).addRecord(p1.getAction());
                    break;
                }
                case "Add Spaces": {
                    OpeningNewPlacesInACourse openingNewPlacesInACourse = new OpeningNewPlacesInACourse(p1.getCourse(), p1.getNumber());
                    openingNewPlacesInACourse.getResult().subscribe(() -> countDownLatch.countDown());
                    actorThreadPool.submit(openingNewPlacesInACourse, p1.getCourse(), null);
                    actorThreadPool.getPrivateState(p1.getCourse()).addRecord(p1.getAction());

                    break;
                }
                case "Participate In Course": {
                    ParticipatingInCourse participatingInCourse = new ParticipatingInCourse(p1.getStudent(), p1.getCourse(), p1.getGrade());
                    participatingInCourse.getResult().subscribe(() -> countDownLatch.countDown());
                    actorThreadPool.submit(participatingInCourse, p1.getCourse(), null);
                    actorThreadPool.getPrivateState(p1.getCourse()).addRecord(p1.getAction());
                    break;
                }
                case "Register With Preferences": {
                    RegisterWithPreferences registerWithPreferences = new RegisterWithPreferences(p1.getStudent(), p1.getPreferences(), p1.getGrade());
                    registerWithPreferences.getResult().subscribe(() -> countDownLatch.countDown());
                    actorThreadPool.submit(registerWithPreferences, p1.getStudent(), null);
                    actorThreadPool.getPrivateState(p1.getStudent()).addRecord(p1.getAction());
                    break;
                }
                case "Unregister": {

                    Unregister unregister = new Unregister(p1.getStudent(), p1.getCourse());
                    unregister.getResult().subscribe(() -> countDownLatch.countDown());
                    actorThreadPool.submit(unregister, p1.getCourse(), null);
                    actorThreadPool.getPrivateState(p1.getCourse()).addRecord(p1.getAction());
                    break;
                }
                case "Administrative Check": {
                    CheckAdministrativeObligations checkAdministrativeObligations = new CheckAdministrativeObligations(p1.getDepartment(), p1.getStudents(), warehouse.getSuspendingMutex(p1.getComputer()).getComputer(), p1.getConditions(), warehouse);
                    checkAdministrativeObligations.getResult().subscribe(() -> countDownLatch.countDown());
                    actorThreadPool.submit(checkAdministrativeObligations, p1.getDepartment(), null);
                    actorThreadPool.getPrivateState(p1.getDepartment()).addRecord(p1.getAction());
                    break;
                }
            }
        }

        while (countDownLatch.getCount() != 0) {
        }

        CountDownLatch countDownLatch2 = new CountDownLatch(example.getPhase2().size());
        for (Phase2 p2 : example.getPhase2()) {
            switch (p2.getAction()) {
                case "Add Student": {
                    AddStudent addStudent = new AddStudent(p2.getDepartment(), p2.getStudent());
                    addStudent.getResult().subscribe(() -> countDownLatch2.countDown());
                    actorThreadPool.submit(addStudent, p2.getDepartment(), new DepartmentPrivateState());
                    actorThreadPool.getPrivateState(p2.getDepartment()).addRecord(p2.getAction());
                    break;
                }
                case "Close Course": {
                    CloseACourse closeACourse = new CloseACourse(p2.getDepartment(), p2.getCourse());
                    closeACourse.getResult().subscribe(() -> countDownLatch2.countDown());
                    actorThreadPool.submit(closeACourse, p2.getDepartment(), null);
                    actorThreadPool.getPrivateState(p2.getDepartment()).addRecord(p2.getAction());
                    break;
                }
                case "Add Spaces": {
                    OpeningNewPlacesInACourse openingNewPlacesInACourse = new OpeningNewPlacesInACourse(p2.getCourse(), p2.getNumber());
                    openingNewPlacesInACourse.getResult().subscribe(() -> countDownLatch2.countDown());
                    actorThreadPool.submit(openingNewPlacesInACourse, p2.getCourse(), null);
                    actorThreadPool.getPrivateState(p2.getCourse()).addRecord(p2.getAction());
                    break;
                }
                case "Participate In Course": {
                    ParticipatingInCourse participatingInCourse = new ParticipatingInCourse(p2.getStudent(), p2.getCourse(), p2.getGrade());
                    participatingInCourse.getResult().subscribe(() -> countDownLatch2.countDown());
                    actorThreadPool.submit(participatingInCourse, p2.getCourse(), null);
                    actorThreadPool.getPrivateState(p2.getCourse()).addRecord(p2.getAction());
                    break;
                }
                case "Register With Preferences": {
                    RegisterWithPreferences registerWithPreferences = new RegisterWithPreferences(p2.getStudent(), p2.getPreferences(), p2.getGrade());
                    registerWithPreferences.getResult().subscribe(() -> countDownLatch2.countDown());
                    actorThreadPool.submit(registerWithPreferences, p2.getStudent(), null);
                    actorThreadPool.getPrivateState(p2.getStudent()).addRecord(p2.getAction());
                    break;
                }
                case "Unregister": {
                    Unregister unregister = new Unregister(p2.getStudent(), p2.getCourse());
                    unregister.getResult().subscribe(() -> countDownLatch2.countDown());
                    actorThreadPool.submit(unregister, p2.getCourse(), null);
                    actorThreadPool.getPrivateState(p2.getCourse()).addRecord(p2.getAction());
                    break;
                }
                case "Administrative Check": {
                    CheckAdministrativeObligations checkAdministrativeObligations = new CheckAdministrativeObligations(p2.getDepartment(), p2.getStudents(), warehouse.getSuspendingMutex(p2.getComputer()).getComputer(), p2.getConditions(), warehouse);
                    checkAdministrativeObligations.getResult().subscribe(() -> countDownLatch2.countDown());
                    actorThreadPool.submit(checkAdministrativeObligations, p2.getDepartment(), null);
                    actorThreadPool.getPrivateState(p2.getDepartment()).addRecord(p2.getAction());
                    break;
                }
            }
        }

        while (countDownLatch2.getCount() != 0) {
        }

        CountDownLatch countDownLatch3 = new CountDownLatch(example.getPhase3().size());
        for (Phase3 p3 : example.getPhase3()) {

            switch (p3.getAction()) {

                case "Add Student": {
                    AddStudent addStudent = new AddStudent(p3.getDepartment(), p3.getStudent());
                    addStudent.getResult().subscribe(() -> countDownLatch3.countDown());
                    actorThreadPool.submit(addStudent, p3.getDepartment(), new DepartmentPrivateState());
                    actorThreadPool.getPrivateState(p3.getDepartment()).addRecord(p3.getAction());
                    break;
                }
                case "Close Course": {
                    CloseACourse closeACourse = new CloseACourse(p3.getDepartment(), p3.getCourse());
                    closeACourse.getResult().subscribe(() -> countDownLatch3.countDown());
                    actorThreadPool.submit(closeACourse, p3.getDepartment(), null);
                    actorThreadPool.getPrivateState(p3.getDepartment()).addRecord(p3.getAction());
                    break;
                }
                case "Add Spaces": {
                    OpeningNewPlacesInACourse openingNewPlacesInACourse = new OpeningNewPlacesInACourse(p3.getCourse(), p3.getNumber());
                    openingNewPlacesInACourse.getResult().subscribe(() -> countDownLatch3.countDown());
                    actorThreadPool.submit(openingNewPlacesInACourse, p3.getCourse(), null);
                    actorThreadPool.getPrivateState(p3.getCourse()).addRecord(p3.getAction());
                    break;

                }
                case "Participate In Course": {
                    ParticipatingInCourse participatingInCourse = new ParticipatingInCourse(p3.getStudent(), p3.getCourse(), p3.getGrade());
                    participatingInCourse.getResult().subscribe(() -> countDownLatch3.countDown());
                    actorThreadPool.submit(participatingInCourse, p3.getCourse(), null);
                    actorThreadPool.getPrivateState(p3.getCourse()).addRecord(p3.getAction());
                    break;
                }
                case "Register With Preferences": {
                    RegisterWithPreferences registerWithPreferences = new RegisterWithPreferences(p3.getStudent(), p3.getPreferences(), p3.getGrade());
                    registerWithPreferences.getResult().subscribe(() -> {countDownLatch3.countDown();
                    });
                    actorThreadPool.submit(registerWithPreferences, p3.getStudent(), null);
                    actorThreadPool.getPrivateState(p3.getStudent()).addRecord(p3.getAction());
                    break;
                }
                case "Unregister": {
                    Unregister unregister = new Unregister(p3.getStudent(), p3.getCourse());
                    unregister.getResult().subscribe(() -> {
                        countDownLatch3.countDown();
                    });
                    actorThreadPool.submit(unregister, p3.getCourse(), null);
                    actorThreadPool.getPrivateState(p3.getCourse()).addRecord(p3.getAction());
                    break;
                }
                case "Administrative Check": {
                    CheckAdministrativeObligations checkAdministrativeObligations = new CheckAdministrativeObligations(p3.getDepartment(), p3.getStudents(), warehouse.getSuspendingMutex(p3.getComputer()).getComputer(), p3.getConditions(), warehouse);
                    checkAdministrativeObligations.getResult().subscribe(() -> countDownLatch3.countDown());
                    actorThreadPool.submit(checkAdministrativeObligations, p3.getDepartment(), null);
                    actorThreadPool.getPrivateState(p3.getDepartment()).addRecord(p3.getAction());
                    break;
                }
            }
        }
        while (countDownLatch3.getCount() != 0) {
        }

    }


    /**
     * attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
     *
     * @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
     */
    public static void attachActorThreadPool(ActorThreadPool myActorThreadPool) {
        actorThreadPool = myActorThreadPool;
    }

    /**
     * shut down the simulation
     * returns list of private states
     */
    public static HashMap<String, PrivateState> end() {
        HashMap<String, PrivateState> ans = (HashMap) actorThreadPool.getActors();
        try {
            actorThreadPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * main function which executing this program
     *
     * @param args argument of json location
     */
    public static void main(String[] args) {
        File f = new File(args[0]);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(f));
            Gson gson = new GsonBuilder().create();
            example = gson.fromJson(reader, Example.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        attachActorThreadPool(new ActorThreadPool(example.getThreads()));

        start();

        HashMap<String, PrivateState> simulationResult = end();
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("result.ser");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(simulationResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

