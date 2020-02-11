package com.ucareer.builder.landing.web;


import com.ucareer.builder.core.CoreResponseBody;
import com.ucareer.builder.landing.models.Landing;
import com.ucareer.builder.user.User;
import com.ucareer.builder.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LandingController {

    @Autowired
    UserService userService;

    @GetMapping("/me/builder")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> mineBuilder(@RequestHeader("Authorization") String authHeader) {
        String token = this.getJwtTokenFromHeader(authHeader);
        CoreResponseBody res;
        if (token == "") {
            res = new CoreResponseBody(null, "invalid token", new Exception("invalid token"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }

        User user = userService.getUserByToken(token);
        //todo: way 1: builderService => getBuilderByUser
        //    todo: create builderRepository => findBuilderByUser Builder obj

        // todo: modified
        if (user == null) {
            res = new CoreResponseBody(null, "invalid token", new Exception("invalid token"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        res = new CoreResponseBody(user, "get user by username", null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @PostMapping("/me/builder")
    public ResponseEntity<CoreResponseBody> update(@RequestHeader("Authorization") String authHeader, @RequestBody Landing builder) {
        CoreResponseBody res;
        String token = this.getJwtTokenFromHeader(authHeader);
        if (token == "") {
            res = new CoreResponseBody(null, "invalid token", new Exception("invalid token"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }


        User user = userService.getUserByToken(token);

//        Landing b = user.getLanding();
//
//        b.setName(builder.getName());
//
//        Head h = b.getHead();
//
//        h.setImgUrl(builder.getHead().getImgUrl());
//        h.setDescription(builder.getHead().getDescription());
//        h.setTitle(builder.getHead().getTitle());
//
//        Gallery g = b.getGallery();
//        g.setDescription(builder.getGallery().getDescription());
//        g.setTitle(builder.getGallery().getTitle());
//
//        if (builder.getGallery().getGalleryItem().size() > 0) {
//            g.getGalleryItem().clear();
//
//            for (GalleryItem item : builder.getGallery().getGalleryItem()) {
//                item.setGallery(g);
//                g.getGalleryItem().add(item);
//
//            }
//            //g.setGalleryItem(itemsSet);
//        }
//
//        Menu m = b.getMenu();
//        m.setTitle(builder.getMenu().getTitle());
//        m.setDescription(builder.getMenu().getDescription());
//
//        if (builder.getMenu().getMenuItems().size() > 0) {
//            m.getMenuItems().clear();
//            for (MenuItem item : builder.getMenu().getMenuItems()) {
//                item.setMenu(m);
//                m.getMenuItems().add(item);
//            }
//            //m.setMenuItems(itemsSet);
//        }
//
//
//
//        builderRepository.save(b);

        //res = new CoreResponseBody(b, "update completed", null);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/builder/getMine")
    public ResponseEntity<CoreResponseBody> update(@RequestHeader("Authorization") String authHeader) {
        CoreResponseBody res;
        String token = this.getJwtTokenFromHeader(authHeader);
        if (token == "") {
            res = new CoreResponseBody(null, "invalid token", new Exception("invalid token"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }



        User user = userService.getUserByToken(token);
       //
        // res = new CoreResponseBody(user.getBuilder(), "test", null);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/builder/save")
    public ResponseEntity<CoreResponseBody> save(@RequestHeader("Authorization") String authHeader, @RequestBody Landing builder) {
        CoreResponseBody res;

        String token = this.getJwtTokenFromHeader(authHeader);
        if (token == "") {
            res = new CoreResponseBody(null, "invalid token", new Exception("invalid token"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }

        User user = userService.getUserByToken(token);
//        Builder b = new Builder();
//        b.setUser(user);
//        b.setName("test");
//
//        //  builderRepository.saveAndFlush(b);
//
//        Head h = new Head();
//        h.setBuilder(b);
//        h.setTitle("head title");
//        h.setDescription("head desc");
//        h.setImgUrl("https://via.placeholder.com/150");
//
//        //  headRepository.save(h);
//
//
//        Gallery g = new Gallery();
//        g.setBuilder(b);
//        g.setTitle("g title");
//        g.setDescription("g desc");
//
//
//        // galleryRepository.saveAndFlush(g);
//
//        GalleryItem gi = new GalleryItem();
//        gi.setGallery(g);
//        gi.setTitle("g1 title");
//        gi.setDescription("g1 desc");
//        gi.setImageUrl("g1 image url");
//
//        GalleryItem g2 = new GalleryItem();
//        g2.setGallery(g);
//        g2.setTitle("g2 title");
//        g2.setDescription("g2 desc");
//        g2.setImageUrl("g2 image url");
//
//
//
//        Set<GalleryItem> itemsSet = new HashSet<GalleryItem>();
//        itemsSet.add(gi);
//        itemsSet.add(g2);
//
//        g.setGalleryItem(itemsSet);
//
//        //  galleryRepository.saveAndFlush(g);
//        b.setHead(h);
//        b.setGallery(g);
//
//        Menu m = new Menu();
//        m.setDescription("menu desc");
//        m.setTitle("menu title");
//        m.setBuilder(b);
//
//        Set<MenuItem> menuItems = new HashSet<>();
//        MenuItem mi1 = new MenuItem();
//        mi1.setCategory("breakfast");
//        mi1.setDescription("menu item 1 desc");
//        mi1.setName("mi1");
//        mi1.setPrice("100");
//
//        MenuItem mi2 = new MenuItem();
//        mi2.setCategory("breakfast");
//        mi2.setDescription("menu item 2 desc");
//        mi2.setName("mi2");
//        mi2.setPrice("50");
//
//        MenuItem mi3 = new MenuItem();
//
//        mi3.setCategory("dinner");
//        mi3.setDescription("menu item 3 desc");
//        mi3.setName("mi3");
//        mi3.setPrice("30");
//
//        MenuItem mi4 = new MenuItem();
//        mi4.setCategory("lunch");
//        mi4.setDescription("menu item 4 desc");
//        mi4.setName("mi4");
//        mi4.setPrice("40");
//
//        mi1.setMenu(m);
//        mi2.setMenu(m);
//        mi3.setMenu(m);
//        mi4.setMenu(m);
//
//        menuItems.add(mi1);
//        menuItems.add(mi2);
//        menuItems.add(mi3);
//        menuItems.add(mi4);
//
//        m.setMenuItems(menuItems);
//        b.setMenu(m);
//
//        user.setBuilder(b);
//
//        builderRepository.save(b);
//
//        res = new CoreResponseBody(b, "test", null);
        return ResponseEntity.ok(null);
    }


    private String getJwtTokenFromHeader(String authHeader) {
        return authHeader.replace("Bearer ", "").trim();
    }


}