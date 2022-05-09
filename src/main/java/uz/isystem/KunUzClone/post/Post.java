package uz.isystem.KunUzClone.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.isystem.KunUzClone.category.Category;
import uz.isystem.KunUzClone.region.Region;
import uz.isystem.KunUzClone.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = ("posts"))
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = ("title"))
    private String title;

    @Column(name = ("content"))
    private String content;

    @ManyToOne
    @JoinColumn(name = ("user_id"), insertable = false, updatable = false)
    private User user;

    @Column(name = ("user_id"))
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = ("region_id"), insertable = false, updatable = false)
    private Region region;

    @Column(name = ("region_id"))
    private Integer regionId;

    @ManyToOne
    @JoinColumn(name = ("category_id"), insertable = false, updatable = false)
    private Category category;

    @Column(name = ("category_id"))
    private Integer categoryId;

    @Column(name = ("token"))
    private String token;

    @Column(name = ("view_count"))
    private String viewCount;

    @Column(name = ("published_at"))
    private String publishedAt;

    @Column(name = ("status"))
    private Boolean status;

    @Column(name = ("created_at"))
    private LocalDateTime createdAt;

    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;

    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;
}
