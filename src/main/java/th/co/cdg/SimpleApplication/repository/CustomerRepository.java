package th.co.cdg.SimpleApplication.repository;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import th.co.cdg.SimpleApplication.model.Customer;

import java.util.ArrayList;

@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    //1. Get All Customers
    @Transactional(Transactional.TxType.SUPPORTS)
    public ArrayList<Customer> queryAllCustomers() {
        String sqlQuery = "SELECT * FROM CUSTOMER";
        Query query = entityManager.createNativeQuery(sqlQuery);

        ArrayList<Object[]> resultList = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Customer> customers = new ArrayList<>();
        for (Object[] row : resultList) {
            Customer customer = new Customer();
            customer.setId((Long) row[0]);
            customer.setName((String) row[1]);
            customer.setSurname((String) row[2]);
            customer.setAddress((String) row[3]);
            customer.setAge((Long) row[4]);
            customer.setTel((String) row[5]);
            customer.setImage((byte[]) row[6]);
            customers.add(customer);
        }
        return customers;
    }

    //2. Get Customer by ID
    @Transactional(Transactional.TxType.SUPPORTS)
    public Customer queryCustomerById(Long id) {
        String sqlQuery = "SELECT * FROM CUSTOMER WHERE ID = :id";
        Query query = entityManager.createNativeQuery(sqlQuery, Customer.class);
        query.setParameter("id", id);
        return (Customer) query.getSingleResult();
    }

    //3. Add Customer
    @Transactional(Transactional.TxType.REQUIRED)
    public int addCustomer(Customer customer) {
        String sqlQuery = "INSERT INTO CUSTOMER (NAME, SURNAME, ADDRESS, AGE, TEL, IMAGE) VALUES (:name, :surname, :address, :age, :tel, :image)";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("name", customer.getName());
        query.setParameter("surname", customer.getSurname());
        query.setParameter("address", customer.getAddress());
        query.setParameter("age", customer.getAge());
        query.setParameter("tel", customer.getTel());
        query.setParameter("image", customer.getImage());

        return query.executeUpdate();
    }

    //4. Delete Customer
    @Transactional(Transactional.TxType.REQUIRED)
    public int deleteCustomer(Long id) {
        String sqlQuery = "DELETE FROM CUSTOMER WHERE ID = :id";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    //5. Update Customer
    @Transactional(Transactional.TxType.REQUIRED)
    public int updateCustomer(Long id, Customer customer) {
        String sqlQuery = "UPDATE CUSTOMER SET ";
        if (customer.getName() != null) {
            sqlQuery += "NAME = '" + customer.getName() + "', ";
        }
        if (customer.getSurname() != null) {
            sqlQuery += "SURNAME = '" + customer.getSurname() + "', ";
        }
        if (customer.getAddress() != null) {
            sqlQuery += "ADDRESS = '" + customer.getAddress() + "', ";
        }
        if (customer.getAge() != null) {
            sqlQuery += "AGE = '" + customer.getAge() + "', ";
        }
        if (customer.getTel() != null) {
            sqlQuery += "TEL = '" + customer.getTel() + "', ";
        }
        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 2);
        sqlQuery += " WHERE ID = :id";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    //Get Customer Image by ID
    @Transactional(Transactional.TxType.SUPPORTS)
    public byte[] queryCustomerImageById(Long id) {
        String sqlQuery = "SELECT IMAGE FROM CUSTOMER WHERE ID = :id";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("id", id);
        return (byte[]) query.getSingleResult();
    }

}
