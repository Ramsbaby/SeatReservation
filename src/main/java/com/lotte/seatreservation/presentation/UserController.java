package com.lotte.seatreservation.presentation;

import com.lotte.seatreservation.application.services.UserService;
import com.lotte.seatreservation.domain.model.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/staff")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/work-status")
    public List<UserResponseDto> allSearch(@PageableDefault(size = 20) Pageable pageable) {
        return userService.findAllUsersWithPagination(pageable);
    }
}
