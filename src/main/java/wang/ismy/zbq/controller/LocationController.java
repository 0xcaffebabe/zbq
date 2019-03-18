package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.LocationDTO;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.service.LocationService;
import wang.ismy.zbq.util.ErrorUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/list")
    @ResultTarget
    public Object selectAll(){
        return locationService.selectAll();
    }

    @PutMapping("")
    @ResultTarget
    @MustLogin
    public Object newLocationRecord(@RequestBody @Valid LocationDTO locationDTO){
        if (locationService.createLocationRecordForCurrentUser(locationDTO) == 1){
            return StringResources.SHARE_LOCATION_SUCCESS;
        }
        ErrorUtils.error(StringResources.SHARE_LOCCATION_FAIL);
        return null;
    }
}
