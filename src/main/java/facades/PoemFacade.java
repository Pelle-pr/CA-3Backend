package facades;

import dto.PoemDTO;
import dto.UserDTO;
import entities.Poem;
import entities.Role;
import entities.User;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class PoemFacade {

    private static EntityManagerFactory emf;
    private static PoemFacade instance;

    private PoemFacade() {
    }

    public static PoemFacade getPoemFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PoemFacade();
        }
        return instance;
    }

    public PoemDTO addPoem (String userName, PoemDTO poemDTO) {

        EntityManager em = emf.createEntityManager();

        User user = em.find(User.class, userName);
        user.getPoemsList().add(new Poem(poemDTO.getContent(), poemDTO.getTitle()));

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            return poemDTO;
        }
        finally {
            em.close();
        }

    }

    public List<PoemDTO> getPoemsByUsername(String username){

        EntityManager em = emf.createEntityManager();

        TypedQuery<Poem> query = em.createQuery("SELECT p from Poem p join p.userList u where u.username = :username", Poem.class);
        query.setParameter("username", username);
        List<Poem> poemList = query.getResultList();


        List<PoemDTO> poemDTOList = new ArrayList<>();
        for (Poem poem: poemList) {
            poemDTOList.add(new PoemDTO(poem));

        }

        return  poemDTOList;
    }

    public void deletePoem (String title, String username) {
        EntityManager em = emf.createEntityManager();
        System.out.println(title);
        User user = em.find(User.class, username);
        Poem poem = em.find(Poem.class, title);

        user.getPoemsList().remove(poem);


        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();


        }finally {
            em.close();
        }
    }

    public List<PoemDTO> getAllPoems () {

        EntityManager em = emf.createEntityManager();

        TypedQuery<Poem> query = em.createQuery("SELECT p from Poem p", Poem.class);
        List<Poem> poemList = query.getResultList();

        List<PoemDTO> poemDTOList = new ArrayList<>();
        for (Poem poem:poemList) {
            poemDTOList.add(new PoemDTO(poem));

        }
        return  poemDTOList;
    }




}
