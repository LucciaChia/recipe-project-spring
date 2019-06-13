package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // uz toto mi vie vyhladat veci, na pozadi sa vykona SQL, nechapem, ako je toto mozne, ze to ide
    Optional<Category> findByDescription(String description);
}
