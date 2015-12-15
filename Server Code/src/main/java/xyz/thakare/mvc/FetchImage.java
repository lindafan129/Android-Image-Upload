package xyz.thakare.mvc;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by akshaythakare on 15/12/15.
 */
@Controller
@RequestMapping("/FetchImage")
public class FetchImage {
    @RequestMapping(method = RequestMethod.POST)
    public void Fetch(HttpServletRequest request, HttpServletResponse response){
        JSONObject req = (JSONObject) JSONValue.parse(request.getParameter("payload"));
        String name;
        name = req.get("name").toString();
        String resp = new ImageHandler().fetchHandler(name);
        try {
            response.setStatus(200);
            response.getWriter().write(resp);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}