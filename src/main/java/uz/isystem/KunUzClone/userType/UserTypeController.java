package uz.isystem.KunUzClone.userType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/user-type")
@RequiredArgsConstructor
public class UserTypeController {
    private final UserTypeService userTypeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserTypeDto dto){
        userTypeService.create(dto);
        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/user-type/create").toUriString());
        return ResponseEntity.created(uri).body("Ok, User Type created!");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userTypeService.getOne(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userTypeService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @Valid @RequestBody UserTypeDto dto){
        userTypeService.update(id, dto);
        return ResponseEntity.ok("Ok, User Type updated!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        userTypeService.delete(id);
        return ResponseEntity.ok("Ok, User Type deleted!");
    }
}
