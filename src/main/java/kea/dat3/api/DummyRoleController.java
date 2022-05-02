package kea.dat3.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

/*
If you start to use this project as the foundation for a real project
make sure to delete this file before you "hand anything in"
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
class DummyResponse {
  String msg;
  String pricipal;
}

  @RestController
  @RequestMapping("/api/message")
  public class DummyRoleController {

    @GetMapping("all")
    public ResponseEntity<?> allMsg() {
      return ResponseEntity.ok(new DummyResponse("This is a message for  (NON-AUTHENTICATED)","anonymous"));
    }

    @RolesAllowed("USER")
    @GetMapping("user")
    public ResponseEntity<?> userMsg(Principal principal) {
      return ResponseEntity.ok(new DummyResponse("This is a PROTECTED message for USERS", principal.getName()));
    }

    @RolesAllowed("ADMIN")
    @GetMapping("admin")
    public ResponseEntity<?> adminMsg(Principal principal) {
      return ResponseEntity.ok(new DummyResponse("This is a PROTECTED message for ADMINS", principal.getName()));
    }

    //The system handles users with more than one role. I suggest you stick to ONE role for ONE user
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("user_admin")
    public ResponseEntity<?> admin_userMsg(Principal principal) {
      return ResponseEntity.ok(new DummyResponse("This is a PROTECTED message for USERS and ADMINS)", principal.getName()));
    }
  }
