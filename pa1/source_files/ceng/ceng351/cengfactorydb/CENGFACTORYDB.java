package ceng.ceng351.cengfactorydb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class CENGFACTORYDB implements ICENGFACTORYDB{
    /**
     * Place your initialization code inside if required.
     *
     * <p>
     * This function will be called before all other operations. If your implementation
     * need initialization , necessary operations should be done inside this function. For
     * example, you can set your connection to the database server inside this function.
     */

    private static String user = "e2521359"; // TODO: Your userName
    private static String password = "TX&I3ml*^qe+"; //  TODO: Your password
    private static String host = "144.122.71.128"; // host name
    private static String database = "db2521359"; // TODO: Your database name
    private static int port = 8080; // port

    private static Connection connection = null;

    public static void connect() {

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =  DriverManager.getConnection(url, user, password);
            return;
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void disconnect() {

        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public void initialize() {
        connect();
    }

    /**
     * Should create the necessary tables when called.
     *
     * @return the number of tables that are created successfully.
     */
    public int createTables() {
        int t = 0;

        String[] createTableStatements = {
                "create table  Factory (" +
                        "factoryId int primary key," +
                        "factoryName text," +
                        "factoryType text," +
                        "country text)",
                "create table  Employee (" +
                        "employeeId int primary key," +
                        "employeeName text," +
                        "department text," +
                        "salary int)",
                "create table    Works_In (" +
                        "factoryId int," +
                        "employeeId int," +
                        "startDate date," +
                        "primary key (factoryId, employeeId)," +
                        "foreign key (factoryId) references Factory(factoryId) on delete cascade on update cascade," +
                        "foreign key (employeeId) references Employee(employeeId) on delete cascade on update cascade)",
                "create table   Product (" +
                        "productId int primary key," +
                        "productName text," +
                        "productType text)",
                "create table   Produce (" +
                        "factoryId int," +
                        "productId int," +
                        "amount int," +
                        "productionCost int," +
                        "primary key (factoryId, productId)," +
                        "foreign key (factoryId) references Factory(factoryId) on delete cascade on update cascade," +
                        "foreign key (productId) references Product(productId) on delete cascade on update cascade)",
                "create table   Shipment (" +
                        "factoryId int," +
                        "productId int," +
                        "amount int," +
                        "pricePerUnit int," +
                        "primary key (factoryId, productId)," +
                        "foreign key (factoryId) references Factory(factoryId)on delete cascade on update cascade," +
                        "foreign key (productId) references Product(productId) on delete cascade on update cascade )"
        };

        try {
            Statement statement = connection.createStatement();

            for (String st : createTableStatements) {
                try {
                     statement.executeUpdate(st);
                     t +=1;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return t;
    }


    /**
     * Should drop the tables if exists when called.
     *
     * @return the number of tables are dropped successfully.
     */
    public int dropTables() {
        int t = 0;

        try {
            Statement statement = connection.createStatement();
            String[] tableNames = {"Works_In","Shipment", "Produce", "Product",  "Employee", "Factory"};

            for (String tableName : tableNames) {
                try {
                    statement.executeUpdate("drop table " + tableName);
                    t+=1;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return t;
    }




    /**
     * Should insert an array of Factory into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertFactory(Factory[] factories) {
        int r = 0;

        try {
            Statement statement = connection.createStatement();

            for (Factory factory : factories) {
                try {
                    String query = String.format(
                            "insert into Factory (factoryId, factoryName, factoryType, country) values (%d, '%s', '%s', '%s')",
                            factory.getFactoryId(), factory.getFactoryName(), factory.getFactoryType(), factory.getCountry());

                    int result = statement.executeUpdate(query);
                    r += (result > 0) ? 1 : 0;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return r;
    }

    public int insertEmployee(Employee[] employees) {
        int r = 0;

        try {
            Statement statement = connection.createStatement();

            for (Employee employee : employees) {
                try {
                    String query = String.format(
                            "insert into Employee (employeeId, employeeName, department, salary) values (%d, '%s', '%s', %d)",
                            employee.getEmployeeId(), employee.getEmployeeName(), employee.getDepartment(), employee.getSalary());

                    int result = statement.executeUpdate(query);
                    r += (result > 0) ? 1 : 0;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return r;
    }



    /**
     * Should insert an array of WorksIn into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertWorksIn(WorksIn[] worksIns) {
        int r = 0;

        try {
            Statement statement = connection.createStatement();

            for (WorksIn worksIn : worksIns) {
                try {
                    String query = String.format(
                            "insert into Works_In (factoryId, employeeId, startdate) values (%d, %d, '%s')",
                            worksIn.getFactoryId(), worksIn.getEmployeeId(), worksIn.getStartDate());

                    int result = statement.executeUpdate(query);
                    r += (result > 0) ? 1 : 0;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return r;
    }


    /**
     * Should insert an array of Product into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduct(Product[] products) {
        int r = 0;

        try {
            Statement statement = connection.createStatement();

            for (Product product : products) {
                try {
                    String q = String.format(
                            "insert into Product (productId, productName, productType) values (%d, '%s', '%s')",
                            product.getProductId(), product.getProductName(), product.getProductType());

                    int result = statement.executeUpdate(q);
                    r += (result > 0) ? 1 : 0;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return r;
    }



    /**
     * Should insert an array of Produce into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduce(Produce[] produces) {
        int r = 0;

        try {
            Statement statement = connection.createStatement();

            for (Produce produce : produces) {
                try {
                    String query = String.format(
                            "insert into Produce (factoryId, productId, amount, productionCost) values (%d, %d, %d, %d)",
                            produce.getFactoryId(), produce.getProductId(), produce.getAmount(), produce.getProductionCost());

                    int result = statement.executeUpdate(query);
                    r += (result > 0) ? 1 : 0;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return r;
    }



    /**
     * Should insert an array of Shipment into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertShipment(Shipment[] shipments) {
        int r = 0;

        try {
            Statement statement = connection.createStatement();

            for (Shipment shipment : shipments) {
                try {
                    String query = String.format(
                            "insert into Shipment (factoryId, productId, amount, pricePerUnit) values (%d, %d, %d, %d)",
                            shipment.getFactoryId(), shipment.getProductId(), shipment.getAmount(), shipment.getPricePerUnit());

                    int result = statement.executeUpdate(query);
                    r += (result > 0) ? 1 : 0;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return r;
    }


    /**
     * Should return all factories that are located in a particular country.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesForGivenCountry(String country) {
        List<Factory> factoriesList = new ArrayList<>();

        try {
            String q = "select distinct * from Factory where country = ? order by factoryId asc";
            PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, country);

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                int fId = res.getInt("factoryId");
                String fName = res.getString("factoryName");
                String fType = res.getString("factoryType");
                String fCountry = res.getString("country");

                Factory factory = new Factory(fId, fName, fType, fCountry);
                factoriesList.add(factory);
            }

            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return factoriesList.toArray(new Factory[0]);
    }



    /**
     * Should return all factories without any working employees.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesWithoutAnyEmployee() {
        List<Factory> factoriesList = new ArrayList<>();

        try {
            String q = "select distinct f.* from Factory f " +
                    "where not exists (select * " +
                    "                  from Works_In w " +
                    "                  where f.factoryId = w.factoryId) " +
                    "order by f.factoryId asc";

            PreparedStatement statement = connection.prepareStatement(q);
                 ResultSet res = statement.executeQuery();

                while (res.next()) {
                    int fId = res.getInt("factoryId");
                    String fName = res.getString("factoryName");
                    String fType = res.getString("factoryType");
                    String fCountry = res.getString("country");

                    Factory factory = new Factory(fId, fName, fType, fCountry);
                    factoriesList.add(factory);
                }


        } catch (Exception e) {
            System.out.println(e);
        }

        return factoriesList.toArray(new Factory[0]);
    }


    /**
     * Should return all factories that produce all products for a particular productType
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesProducingAllForGivenType(String productType) {
        List<Factory> factoriesList = new ArrayList<>();

        String q = "select distinct f.* " +
                "from Factory f " +
                "where not exists (" +
                "    select pr.productId " +
                "    from Product pr " +
                "    where pr.productType = ? " +
                "    and not exists (" +
                "        select p.factoryId " +
                "        from Produce p " +
                "        where p.factoryId = f.factoryId " +
                "        and p.productId = pr.productId" +
                "    )" +
                ") order by f.factoryId asc;";

        try (PreparedStatement statement = connection.prepareStatement(q)) {
            statement.setString(1, productType);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int fId = resultSet.getInt("factoryId");
                String fName = resultSet.getString("factoryName");
                String fType = resultSet.getString("factoryType");
                String fCountry = resultSet.getString("country");

                Factory factory = new Factory(fId, fName, fType, fCountry);
                factoriesList.add(factory);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return factoriesList.toArray(new Factory[0]);
    }





    /**
     * Should return the products that are produced in a particular factory but
     * don’t have any shipment from that factory.
     *
     * @return Product[]
     */
    public Product[] getProductsProducedNotShipped() {
        List<Product> productList = new ArrayList<>();

        String q = "select distinct p.* " +
                "from Produce pr, Product p " +
                "where pr.productid = p.productid " +
                "and not EXISTS (" +
                "    select * " +
                "    from Shipment s " +
                "    where s.factoryid = pr.factoryid " +
                "    and s.productid = pr.productid" +
                ") " +
                "order by p.productid asc;";

        try  {
            PreparedStatement statement = connection.prepareStatement(q);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int pId = resultSet.getInt("productId");
                String pName = resultSet.getString("productName");
                String pResult = resultSet.getString("productType");

                Product product = new Product(pId, pName, pResult);
                productList.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return productList.toArray(new Product[0]);
    }




    /**
     * For a given factoryId and department, should return the average salary of
     *     the employees working in that factory and that specific department.
     *
     * @return double
     */
    public double getAverageSalaryForFactoryDepartment(int factoryId, String department) {
        double averageSalary = 0;

        String q = "select avg(e.salary) as average " +
                "from Employee e " +
                "where e.employeeId in (" +
                "    select w.employeeId " +
                "    from Works_In w " +
                "    where w.factoryId = ?" +
                ") " +
                "and e.department = ?";

        try (PreparedStatement statement = connection.prepareStatement(q)) {
            statement.setInt(1, factoryId);
            statement.setString(2, department);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                averageSalary = resultSet.getDouble("average");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return averageSalary;
    }






    /**
     * Should return the most profitable products for each factory
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProducts() {
        List<QueryResult.MostValueableProduct> productList = new ArrayList<>();

        String q = "select distinct " +
                "f.factoryId, " +
                "p.productId, " +
                "p.productName, " +
                "p.productType, " +
                "ifnull(s.amount * s.pricePerUnit, 0) - pr.amount * pr.productionCost as profit " +
                "from " +
                "Factory f " +
                "join Produce pr on f.factoryId = pr.factoryId " +
                "join Product p on pr.productId = p.productId " +
                "left join Shipment s on f.factoryId = s.factoryId and p.productId = s.productId " +
                "where " +
                "ifnull(s.amount * s.pricePerUnit, 0) - pr.amount * pr.productionCost = (" +
                "       select max(ifnull(s2.amount * s2.pricePerUnit, 0) - pr2.amount * pr2.productionCost) as profit " +
                "       from Factory f2 " +
                "       join Produce pr2 on f2.factoryId = pr2.factoryId " +
                "       join Product p2 on pr2.productId = p2.productId " +
                "       left join Shipment s2 on f2.factoryId = s2.factoryId and p2.productId = s2.productId " +
                "       where f2.factoryId = f.factoryId " +
                ") " +
                "order by profit desc, f.factoryId asc;";




        try (PreparedStatement statement = connection.prepareStatement(q)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int fId = resultSet.getInt("factoryId");
                int pId = resultSet.getInt("productId");
                String pName = resultSet.getString("productName");
                String pType = resultSet.getString("productType");
                double profit = resultSet.getDouble("profit");

                QueryResult.MostValueableProduct product = new QueryResult.MostValueableProduct(
                        fId, pId, pName, pType, profit);

                productList.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return productList.toArray(new QueryResult.MostValueableProduct[0]);
    }


    /**
     * For each product, return the factories that gather the highest profit
     * for that product
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProductsOnFactory() {
        List<QueryResult.MostValueableProduct> productList = new ArrayList<>();

        String q = "select distinct " +
                "f.factoryId, " +
                "p.productId, " +
                "p.productName, " +
                "p.productType, " +
                "ifnull(s.amount * s.pricePerUnit, 0) - pr.amount * pr.productionCost as profit " +
                "from " +
                "Factory f " +
                "join Produce pr on f.factoryId = pr.factoryId " +
                "join Product p on pr.productId = p.productId " +
                "left join Shipment s on f.factoryId = s.factoryId and p.productId = s.productId " +
                "where " +
                "ifnull(s.amount * s.pricePerUnit, 0) - pr.amount * pr.productionCost = (" +
                "       select max(ifnull(s2.amount * s2.pricePerUnit, 0) - pr2.amount * pr2.productionCost) as profit " +
                "       from Factory f2 " +
                "       join Produce pr2 on f2.factoryId = pr2.factoryId " +
                "       join Product p2 on pr2.productId = p2.productId " +
                "       left join Shipment s2 on f2.factoryId = s2.factoryId and p2.productId = s2.productId " +
                "       where p.productId = p2.productId " +
                ") " +
                "order by profit desc, p.productId asc;";


        try (PreparedStatement statement = connection.prepareStatement(q)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int fId = resultSet.getInt("factoryId");
                int pId = resultSet.getInt("productId");
                String pName = resultSet.getString("productName");
                String pType = resultSet.getString("productType");
                double profit = resultSet.getDouble("profit");

                QueryResult.MostValueableProduct product = new QueryResult.MostValueableProduct(
                        fId, pId, pName, pType, profit);

                productList.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return productList.toArray(new QueryResult.MostValueableProduct[0]);
    }




    /**
     * For each department, should return all employees that are paid under the
     *     average salary for that department. You consider the employees
     *     that do not work earning ”0”.
     *
     * @return QueryResult.LowSalaryEmployees[]
     */
    public QueryResult.LowSalaryEmployees[] getLowSalaryEmployeesForDepartments() {
        List<QueryResult.LowSalaryEmployees> employeeList = new ArrayList<>();

        /* String q = "select distinct " +
                "e.employeeId, " +
                "e.employeeName, " +
                "e.department, " +
                "coalesce(e.salary, 0) AS salary " +
                "from Employee e " +
                "where " +
                "coalesce(e.salary, 0) < (" +
                "select " +
                "avg(coalesce(e2.salary, 0)) " +
                "from Employee e2 " +
                "where e2.department = e.department) " +
                "order by " +
                "e.employeeId asc;"; */

        String q = "select * from (" +
                "select distinct " +
                "e.employeeId, " +
                "e.employeeName, " +
                "e.department, " +
                "coalesce(e.salary, 0) AS salary " +
                "from " +
                "Employee e " +
                "where " +
                "(e.employeeId in (select distinct w3.employeeId from Works_In w3) and " +
                "coalesce(e.salary, 0) < (" +
                "select coalesce(" +
                "(select sum(coalesce(e2.salary, 0)) " +
                "from Employee e2 " +
                "where e2.department = e.department and e2.employeeId in (" +
                "select distinct w2.employeeId " +
                "from Works_In w2" +
                ")), " +
                "0" +
                ") / nullif(" +
                "(select count(distinct m.employeeId) " +
                "from Employee m " +
                "where m.department = e.department), " +
                "0" +
                ")" +
                ")) " +
                "union " +
                "select distinct " +
                "e.employeeId, " +
                "e.employeeName, " +
                "e.department, " +
                "0 as salary " +
                "from " +
                "Employee e " +
                "where " +
                "(e.employeeId not in (select distinct w3.employeeId from Works_In w3) and " +
                "0 < (" +
                "select coalesce(" +
                "(select sum(coalesce(e2.salary, 0)) " +
                "from Employee e2 " +
                "where e2.department = e.department and e2.employeeId in (" +
                "select distinct w2.employeeId " +
                "from Works_In w2" +
                ")), " +
                "0" +
                ") / nullif(" +
                "(select count(distinct m.employeeId) " +
                "from Employee m " +
                "where m.department = e.department), " +
                "0" +
                ")" +
                "))" +
                ") as subquery " +
                "order by " +
                "subquery.employeeId asc;";





        try (PreparedStatement statement = connection.prepareStatement(q)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employeeId");
                String employeeName = resultSet.getString("employeeName");
                String department = resultSet.getString("department");
                int salary = resultSet.getInt("salary");

                QueryResult.LowSalaryEmployees employee = new QueryResult.LowSalaryEmployees(employeeId, employeeName, department, salary);
                employeeList.add(employee);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return employeeList.toArray(new QueryResult.LowSalaryEmployees[0]);
    }



    /**
     * For the products of given productType, increase the productionCost of every unit by a given percentage.
     *
     * @return number of rows affected
     */
    public int increaseCost(String productType, double percentage) {
        int r = 0;

        // SQL query to update the productionCost
        String q = "update Produce set productionCost =  (productionCost * (100 + ?) / 100) " +
                "where productId in (select productId from Product where productType = ?)";

        try (PreparedStatement statement = connection.prepareStatement(q)) {
            statement.setDouble(1, percentage);
            statement.setString(2, productType);

            r = statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

        return r;
    }



    /**
     * Deleting all employees that have not worked since the given date.
     *
     * @return number of rows affected
     */
    public int deleteNotWorkingEmployees(String givenDate) {
        int r = 0;

        String q = "delete from Employee where employeeId  not in " +
                "(select employeeId from Works_In where startDate >= ?)";

        try (PreparedStatement statement = connection.prepareStatement(q)) {
            statement.setString(1, givenDate);

            r = statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

        return r;
    }




    /**
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     *  THE METHODS AFTER THIS LINE WILL NOT BE GRADED.
     *  YOU DON'T HAVE TO SOLVE THEM, LEAVE THEM AS IS IF YOU WANT.
     *  IF YOU HAVE ANY QUESTIONS, REACH ME VIA EMAIL.
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     */

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Peers are considered tied and receive the same rank. After
     * that, there should be a gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Everything is the same but after ties, there should be no
     * gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank2() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each factory, calculate the most profitable 4th product.
     *
     * @return QueryResult.FactoryProfit
     */
    public QueryResult.FactoryProfit calculateFourth() {
        return new QueryResult.FactoryProfit(0,0,0);
    }

    /**
     * Determine the salary variance between an employee and another
     * one who began working immediately after the first employee (by
     * startDate), for all employees.
     *
     * @return QueryResult.SalaryVariant[]
     */
    public QueryResult.SalaryVariant[] calculateVariance() {
        return new QueryResult.SalaryVariant[0];
    }

    /**
     * Create a method that is called once and whenever a Product starts
     * losing money, deletes it from Produce table
     *
     * @return void
     */
    public void deleteLosing() {

    }
}
