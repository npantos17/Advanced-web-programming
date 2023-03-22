//package com.example.dom3.repository;
//
//import com.example.dom3.models.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.support.JpaEntityInformation;
//import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//
//@Repository("userRepository")
//public class CustomUserRepository extends SimpleJpaRepository<User, Long> implements UserRepository{
//
//    private final EntityManager entityManager;
//
//
//    @Autowired
//    public CustomUserRepository(EntityManager entityManager)
//    {
//        super(User.class, entityManager);
//        this.entityManager = entityManager;
//    }
//
//    public CustomUserRepository(JpaEntityInformation<User, ?> entityInformation, EntityManager entityManager) {
//        super(entityInformation, entityManager);
//        this.entityManager = entityManager;
//    }
//
//    public CustomUserRepository(Class<User> domainClass, EntityManager em) {
//        super(domainClass, em);
//        this.entityManager = em;
//    }
//
//    @Override
//    public User findByUsername(String name) {
////        return entityManager.createQuery();
//        return null;
//    }
//
//    @Override
//    public User findByEmail(String email) {
//        return (User) entityManager.createQuery("select u from User u where u.email like :email")
//                .setParameter("email", "%"+email+"%").getResultList().get(0);
//
//    }
//}
