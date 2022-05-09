package uz.isystem.KunUzClone.region.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.isystem.KunUzClone.region.Region;
import uz.isystem.KunUzClone.region.RegionDto;
import uz.isystem.KunUzClone.region.RegionRepository;
import uz.isystem.KunUzClone.region.RegionService;
import uz.isystem.KunUzClone.www.exception.ApiRequestException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final ModelMapper mapper;
    @Override
    public void create(RegionDto regionDto) {
        Region region = mapper.map(regionDto, Region.class);
        region.setCreatedAt(LocalDateTime.now());
        region.setStatus(true);
        regionRepository.save(region);
    }

    @Override
    public RegionDto getOne(Integer id) {
        return mapper.map(getEntity(id), RegionDto.class);
    }

    @Override
    public List<RegionDto> getAll() {
        return getAllEntity()
                .stream()
                .map(region -> mapper.map(region, RegionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Integer id, RegionDto regionDto) {
        Region old = getEntity(id);
        Region region = mapper.map(regionDto, Region.class);
        old.setUpdatedAt(LocalDateTime.now());
        old.setName(region.getName());
        regionRepository.save(old);
    }

    @Override
    public void delete(Integer id) {
        Region region = getEntity(id);
        region.setDeletedAt(LocalDateTime.now());
        regionRepository.save(region);
    }

    // |- SECONDARY FUNCTIONS -|

    public Region getEntity(Integer id){
        return regionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(()-> new ApiRequestException("Region not found!"));
    }

    public List<Region> getAllEntity(){

        List<Region> regions = regionRepository.findAllByDeletedAtIsNull();

        if (regions.isEmpty()){
            throw new ApiRequestException("There is no Region Types!");
        }
        return regions;
    }
}
