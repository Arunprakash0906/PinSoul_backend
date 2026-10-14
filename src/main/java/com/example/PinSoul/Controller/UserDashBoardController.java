package com.example.PinSoul.Controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PinSoul.Exception.UserException;
import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.LocationCategory;
import com.example.PinSoul.Model.Sign;
import com.example.PinSoul.Model.User;
import com.example.PinSoul.Respository.LocatoinRepository;
import com.example.PinSoul.Respository.SignRepository;
import com.example.PinSoul.Respository.UserRespository;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {
        "http://localhost:5173", "http://127.0.0.1:5173",
        "http://localhost:5174", "http://127.0.0.1:5174"
})
public class UserDashBoardController {
    @Autowired
    private LocatoinRepository locarepo;

    @Autowired
    private SignRepository signRepository;

    @Autowired
    private UserRespository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<Map<String, String>> getProfile(Principal principal) {
        Sign sign = getAuthenticatedSign(principal);
        User profile = userRepository.findBySign(sign).orElse(null);
        if (profile == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(profileResponse(profile));
    }

    @PostMapping("/profile")
    public ResponseEntity<Map<String, String>> saveProfile(
            @RequestBody Map<String, String> details,
            Principal principal) {
        Sign sign = getAuthenticatedSign(principal);
        if (details == null) {
            throw new UserException("Profile details are required");
        }
        String name = details.get("name");
        String email = details.get("email");
        String phone = details.get("phone");
        String address = details.get("address");

        if (isBlank(name) || isBlank(email) || isBlank(phone) || isBlank(address)) {
            throw new UserException("Name, email, phone, and address are required");
        }

        User profile = userRepository.findBySign(sign).orElseGet(User::new);
        profile.setUserName(name.trim());
        profile.setMailId(email.trim());
        profile.setMobileNo(phone.trim());
        profile.setAddress(address.trim());
        profile.setSign(sign);
        User savedProfile = userRepository.save(profile);
        return ResponseEntity.ok(profileResponse(savedProfile));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Long>> getDashboard(Principal principal) {
        Sign sign = getAuthenticatedSign(principal);
        Map<String, Long> categoryCounts = new LinkedHashMap<>();
        for (LocationCategory category : LocationCategory.values()) {
            categoryCounts.put(category.name(), 0L);
        }

        List<Location> locations = locarepo.findLocationsBySignId(sign.getSignId());
        for (Location location : locations) {
            if (location.getCategory() != null) {
                categoryCounts.merge(location.getCategory().name(), 1L, Long::sum);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(categoryCounts);
    }

    private Sign getAuthenticatedSign(Principal principal) {
        if (principal == null || principal.getName() == null || principal.getName().isBlank()) {
            throw new UserException("User authentication is required");
        }
        return signRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserException("User account was not found"));
    }

    private Map<String, String> profileResponse(User profile) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("name", profile.getUserName());
        response.put("email", profile.getMailId());
        response.put("phone", profile.getMobileNo());
        response.put("address", profile.getAddress());
        return response;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
