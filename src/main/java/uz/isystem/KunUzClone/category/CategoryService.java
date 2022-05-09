package uz.isystem.KunUzClone.category;
import java.util.List;

public interface CategoryService {

    // create
    void create(CategoryDto categoryDto);
    // read
    CategoryDto getOne(Integer id);
    List<CategoryDto> getAll();
    // update
    void update(Integer id, CategoryDto categoryDto);
    // delete
    void delete(Integer id);
}
