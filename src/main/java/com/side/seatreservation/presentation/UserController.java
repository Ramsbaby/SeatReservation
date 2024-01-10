package com.side.seatreservation.presentation;

import com.side.seatreservation.application.services.UserService;
import com.side.seatreservation.domain.model.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("api/staff")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/work-status")
    public Flux<UserResponseDto> allSearch(@RequestParam("page") int page,
                                           @RequestParam("size") int size) {
        return userService.findAllUsersWithPagination(PageRequest.of(page, size));
    }
}
