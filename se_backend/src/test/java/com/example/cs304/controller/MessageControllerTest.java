package com.example.cs304.controller;

import com.example.cs304.controller.MessageController;
import com.example.cs304.entity.Message;
import com.example.cs304.repository.MessageRepository;
import com.example.cs304.response.Response;
import com.example.cs304.service.StudentService;
import com.example.cs304.converter.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MessageControllerTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetMessageSuccessfully() {
        String authorization = "validToken";
        String type = "type";
        String SID = "SID";
        Message message = new Message();
        List<Message> messages = Collections.singletonList(message);

        when(jwtTokenProvider.validateToken(authorization)).thenReturn(true);
        when(jwtTokenProvider.getUsername(authorization)).thenReturn(SID);
        when(messageRepository.findByCondition(SID, type)).thenReturn(messages);

        Response<?> response = messageController.getMessage(authorization, type);

        assertEquals(200, response.getStatus());
        assertEquals("Get Successful", response.getMessage());
        assertEquals(messages, response.getData());
    }

    @Test
    void shouldAddMessageSuccessfully() {
        String authorization = "validToken";
        String content = "content";
        String dateTimeStr = "2022-12-31 23:59:59";
        String receiverId = "receiverId";

        when(jwtTokenProvider.validateToken(authorization)).thenReturn(true);
        when(jwtTokenProvider.getUsername(authorization)).thenReturn(receiverId);

        Response<?> response = messageController.addMessage(authorization, Collections.singletonMap("content", content));

        assertEquals(200, response.getStatus());
        assertEquals("Add Successful", response.getMessage());
    }
    @Test
    void testGetMessage_ValidToken_ValidType_MessagesExist() {
        String authorization = "validToken";
        String type = "important";
        String SID = "student123";
        List<Message> expectedMessages = Collections.singletonList(new Message());

        when(jwtTokenProvider.validateToken(authorization)).thenReturn(true);
        when(jwtTokenProvider.getUsername(authorization)).thenReturn(SID);
        when(messageRepository.findByCondition(SID, type)).thenReturn(expectedMessages);

        Response<?> response = messageController.getMessage(authorization, type);

        assertEquals(200, response.getStatus());
        assertEquals("Get Successful", response.getMessage());
        assertEquals(expectedMessages, response.getData());
    }

    @Test
    void testGetMessage_InvalidToken() {
        String authorization = "invalidToken";

        when(jwtTokenProvider.validateToken(authorization)).thenReturn(false);

        Response<?> response = messageController.getMessage(authorization, null);

        assertEquals(401, response.getStatus());
        assertEquals("Invalid Token", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testGetMessage_ValidToken_NoMessagesFound() {
        String authorization = "validToken";
        String type = "normal";
        String SID = "student123";

        when(jwtTokenProvider.validateToken(authorization)).thenReturn(true);
        when(jwtTokenProvider.getUsername(authorization)).thenReturn(SID);
        when(messageRepository.findByCondition(SID, type)).thenReturn(Collections.emptyList());

        Response<?> response = messageController.getMessage(authorization, type);

        assertEquals(200, response.getStatus());
        assertTrue(response.getData() instanceof List);
        assertTrue(((List<?>) response.getData()).isEmpty());
    }
    @Test
    void testAddMessage_ValidToken_ValidContent_ValidEndTime_MessageAdded() {
        String authorization = "validToken";
        String content = "Hello, World!";
        String dateTimeStr = "2024-06-03 10:00:00";
        String SID = "student123";

        when(jwtTokenProvider.validateToken(authorization)).thenReturn(true);
        when(jwtTokenProvider.getUsername(authorization)).thenReturn(SID);

        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        messageRepository.addMessage(content, SID, parsedDateTime, "self");

        Response<?> response = messageController.addMessage(authorization, Map.of("content", content, "endTime", dateTimeStr));

        assertEquals(200, response.getStatus());
        assertEquals("Add Successful", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testAddMessage_InvalidToken() {
        String authorization = "invalidToken";
        Map<String, Object> requestBody = Map.of("content", "Test message", "endTime", "2024-06-03 10:00:00");

        when(jwtTokenProvider.validateToken(authorization)).thenReturn(false);

        Response<?> response = messageController.addMessage(authorization, requestBody);

        assertEquals(401, response.getStatus());
        assertEquals("Invalid Token", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testAddMessage_ValidToken_ContentIsEmpty() {
        String authorization = "validToken";
        String SID = "student123";
        Map<String, Object> requestBody = Map.of("content", "", "endTime", "2024-06-03 10:00:00");

        when(jwtTokenProvider.validateToken(authorization)).thenReturn(true);
        when(jwtTokenProvider.getUsername(authorization)).thenReturn(SID);

        // 假设这里会有验证内容不为空的逻辑
        // 需要根据实际情况调整

        Response<?> response = messageController.addMessage(authorization, requestBody);

        // 根据实际情况调整预期结果
        assertEquals(400, response.getStatus());
        assertEquals("Content cannot be empty", response.getMessage());
        assertNull(response.getData());
    }

// 其他测试用例将遵循类似的模式，设置适当的条件和期望结果。
// 其他测试用例将遵循类似的模式，设置适当的条件和期望结果。

// 更多测试用例...
}
