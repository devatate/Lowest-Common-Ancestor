
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LowestCommonAncestor {

    class Employee{
        String name;
        Employee parent = null;

        Employee(String name){
            this.name = name;
        }

        void addParent(Employee emp){
            this.parent = emp;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public void solve() throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new  FileWriter(System.getenv("OUTPUT_PATH")));
        Map<String, Employee> map = new HashMap<String, Employee>();
        Integer.parseInt(scanner.nextLine());

        Employee firstEmployee = findResult(map);

        bufferedWriter.write(firstEmployee.name);

        bufferedWriter.close();
    }

    private Employee findResult(Map<String, Employee> map) {
        Employee firstEmployee = createEmployee(scanner.nextLine(), map);
        Employee secondEmployee = createEmployee(scanner.nextLine(), map);

        createEmployeeTree(map);

        int nofStepFirstToRoot = findNofPath(firstEmployee);
        int nofStepSecondToRoot = findNofPath(secondEmployee);
        if(nofStepFirstToRoot>nofStepSecondToRoot){
            firstEmployee = goToUpperLevel(firstEmployee, nofStepFirstToRoot-nofStepSecondToRoot);
        }else if(nofStepFirstToRoot<nofStepSecondToRoot){
            secondEmployee = goToUpperLevel(secondEmployee, nofStepSecondToRoot-nofStepFirstToRoot);
        }

        firstEmployee = moveUntilEqual(firstEmployee, secondEmployee);
        return firstEmployee;
    }

    private Employee moveUntilEqual(Employee firstEmployee, Employee secondEmployee) {
        while(!firstEmployee.equals(secondEmployee)){
            firstEmployee = firstEmployee.parent;
            secondEmployee = secondEmployee.parent;
        }
        return firstEmployee;
    }

    private void createEmployeeTree(Map<String, Employee> map) {
        while(scanner.hasNextLine()){
            String[] line = scanner.nextLine().split(" ");
            Employee emp1 = createEmployee(line[0], map);
            Employee emp2 = createEmployee(line[1], map);
            emp2.addParent(emp1);
        }
    }

    public static void main(String[] args) throws IOException {
        new LowestCommonAncestor().solve();
    }

    private Employee createEmployee(String name, Map<String, Employee> map){
        Employee emp = map.getOrDefault(name, new Employee(name));
        map.put(name, emp);
        return emp;
    }

    int findNofPath(Employee e){
        int i=0;
        while(e != null){
            e=e.parent;
            i++;
        }
        return i;
    }

    Employee goToUpperLevel(Employee e, int nof){
        while(nof-->0){
            e= e.parent;
        }
        return e;
    }
}
