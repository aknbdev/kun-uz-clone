package uz.isystem.KunUzClone.category.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.isystem.KunUzClone.category.Category;
import uz.isystem.KunUzClone.category.CategoryDto;
import uz.isystem.KunUzClone.category.CategoryRepository;
import uz.isystem.KunUzClone.category.CategoryService;
import uz.isystem.KunUzClone.www.exception.ApiRequestException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    @Override
    public void create(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        category.setCreatedAt(LocalDateTime.now());
        category.setStatus(true);
        categoryRepository.save(category);
    }

    @Override
    public CategoryDto getOne(Integer id) {
        return mapper.map(getEntity(id), CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAll() {
        return getAllEntity()
                .stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Integer id, CategoryDto categoryDto) {
        Category old = getEntity(id);
        Category category = mapper.map(categoryDto, Category.class);
        old.setName(category.getName());
        old.setUpdatedAt(LocalDateTime.now());
        categoryRepository.save(old);
    }

    @Override
    public void delete(Integer id) {
        Category category = getEntity(id);
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }

    // |- SECONDARY FUNCTIONS -|

    public Category getEntity(Integer id){
        return categoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(()-> new ApiRequestException("Category not found!"));
    }

    public List<Category> getAllEntity(){

        List<Category> categories = categoryRepository.findAllByDeletedAtIsNull();

        if (categories.isEmpty()){
            throw new ApiRequestException("There is no Category Types!");
        }
        return categories;
    }
}
