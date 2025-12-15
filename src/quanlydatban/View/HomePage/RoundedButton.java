/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.View.HomePage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
/**
 *
 * @author Admin
 */
public class RoundedButton extends JButton {
    private int cornerRadius = 10; // Độ cong của góc (điều chỉnh theo ý muốn)

    public RoundedButton() {
        // Thiết lập thuộc tính mặc định
        super();
        setContentAreaFilled(false); // Quan trọng: Tắt vùng nội dung mặc định
        setFocusPainted(false);
        setBorderPainted(false); // Tắt đường viền mặc định
    }

    // Constructor để thiết lập văn bản ban đầu
    public RoundedButton(String text) {
        this();
        setText(text);
    }
    
    // Phương thức này sẽ vẽ lại hình dạng của nút
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Bật chế độ làm mịn (anti-aliasing) để góc bo đẹp hơn
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Lấy màu nền hiện tại của nút
        Color backgroundColor = getBackground();

        // 2. Vẽ hình chữ nhật bo góc với màu nền
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Đảm bảo chữ vẫn được vẽ
        super.paintComponent(g);

        g2.dispose();
    }
    
    // Phương thức này sẽ vẽ lại đường viền (nếu có)
    @Override
    protected void paintBorder(Graphics g) {
        // Không vẽ đường viền (vì đã tắt setBorderPainted(false))
    }

    // Phương thức này giúp Mouse nhận biết hình dạng bo góc
    @Override
    public boolean contains(int x, int y) {
        // Mặc định: Trả về hình chữ nhật, bạn có thể custom phức tạp hơn nếu cần
        return super.contains(x, y); 
    }
}
