package uz.isystem.KunUzClone.post;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.isystem.KunUzClone.category.Category;
import uz.isystem.KunUzClone.region.Region;
import uz.isystem.KunUzClone.user.User;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private User user;
    private Integer userId;
    private Region region;
    private Integer regionId;
    private Category category;
    private Integer categoryId;
    private String token;
    private String viewCount;
    private String publishedAt;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
