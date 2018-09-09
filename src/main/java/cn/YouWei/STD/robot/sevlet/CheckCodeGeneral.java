package cn.YouWei.STD.robot.sevlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author CIACs (2015-6-23)
 * @
 */
public class CheckCodeGeneral extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 禁止图像缓存,使得单击验证码可以刷新验证码图片
        resp.setHeader("Pragma", "nocache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
 
        BufferedImage bim = new BufferedImage(70, 20,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D gc = bim.createGraphics();
        // 设置图片填充颜色
        gc.setColor(Color.yellow);
        gc.fillRect(0, 0, 70, 20);
        // 设置边框颜色
        gc.setColor(Color.black);
        gc.drawRect(0, 0, 69, 19);
        // 产生4位随机数
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        // 设置干扰线颜色
        gc.setColor(Color.blue);
        for (int j = 0; j < 30; j++) {
            int x = rand.nextInt(70);
            int y = rand.nextInt(20);
            int x1 = rand.nextInt(6);
            int y1 = rand.nextInt(6);
            // 往图片里面画干扰线
            gc.drawLine(x, y, x + x1, y + y1);
        }
 
        for (int i = 0; i < 4; i++) {
            int m = rand.nextInt(9);
            // 将生成的数字写入到图片中去,int转成string
            String str = String.valueOf(m);
            // 设置字体颜色
            gc.setColor(Color.RED);
            gc.drawString(str, i * 10 + 20, 15);
            sb.append(m);
        }
        // 将stringbuffer转成string
        String code = String.valueOf(sb);
        // 将生成的验证码的值放到session中去
        HttpSession session = req.getSession();
        session.removeAttribute("code");
        session.setAttribute("code", code);
        // 将图片以流的形式输出
        ServletOutputStream out = resp.getOutputStream();
        ImageIO.write(bim, "jpg", out);
        out.close();

	}

}
