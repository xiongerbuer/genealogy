package com.genealogy.controller;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.controller.OperationLogController;
import com.genealogy.dto.UserPermissionDTO;
import com.genealogy.query.OperationLogQuery;
import com.genealogy.service.OperationLogService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>
 *   操作日志控制器单元测试
 * </p>
 * @author xiongyou
 */
public class OperationLogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OperationLogService operationLogService;

    @InjectMocks
    private OperationLogController operationLogController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(operationLogController).build();
    }

    @Test
    public void testQueryOperationLogs_Success() throws Exception {
        OperationLogQuery query = new OperationLogQuery();
        query.setUserId(1L);
        query.setModule("testModule");
        query.setStartTime(LocalDateTime.now());
        query.setEndTime(LocalDateTime.now());

        UserPermissionDTO dto1 = new UserPermissionDTO();
        dto1.setUserId(1L);
        dto1.setModule("testModule");
        dto1.setPermission("read");
        dto1.setDesc("testDesc1");
        dto1.setIpAddress("127.0.0.1");
        dto1.setUserAgent("testAgent1");

        UserPermissionDTO dto2 = new UserPermissionDTO();
        dto2.setUserId(1L);
        dto2.setModule("testModule");
        dto2.setPermission("write");
        dto2.setDesc("testDesc2");
        dto2.setIpAddress("127.0.0.1");
        dto2.setUserAgent("testAgent2");

        RestResult<List<UserPermissionDTO>> result = new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, Arrays.asList(dto1, dto2));

        when(operationLogService.queryOperationLogs(query)).thenReturn(result);

        mockMvc.perform(get("/operationLog/query")
                .param("userId", "1")
                .param("module", "testModule")
                .param("startTime", String.valueOf(new Date().getTime()))
                .param("endTime", String.valueOf(new Date().getTime()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultCodeConstant.CODE_000000))
                .andExpect(jsonPath("$.msg").value(ResultCodeConstant.CODE_000000_MSG))
                .andExpect(jsonPath("$.data[0].userId").value(1L))
                .andExpect(jsonPath("$.data[0].module").value("testModule"))
                .andExpect(jsonPath("$.data[0].permission").value("read"))
                .andExpect(jsonPath("$.data[0].desc").value("testDesc1"))
                .andExpect(jsonPath("$.data[0].ipAddress").value("127.0.0.1"))
                .andExpect(jsonPath("$.data[0].userAgent").value("testAgent1"))
                .andExpect(jsonPath("$.data[1].userId").value(1L))
                .andExpect(jsonPath("$.data[1].module").value("testModule"))
                .andExpect(jsonPath("$.data[1].permission").value("write"))
                .andExpect(jsonPath("$.data[1].desc").value("testDesc2"))
                .andExpect(jsonPath("$.data[1].ipAddress").value("127.0.0.1"))
                .andExpect(jsonPath("$.data[1].userAgent").value("testAgent2"));

        verify(operationLogService, times(1)).queryOperationLogs(query);
    }

    @Test
    public void testRecordOperationLog_Success() throws Exception {
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setUserId(1L);
        dto.setModule("testModule");
        dto.setPermission("read");
        dto.setDesc("testDesc");
        dto.setIpAddress("127.0.0.1");
        dto.setUserAgent("testAgent");

        RestResult<Void> result = new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG);

        when(operationLogService.recordOperationLog(dto)).thenReturn(result);

        mockMvc.perform(post("/operationLog/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"module\":\"testModule\",\"permission\":\"read\",\"desc\":\"testDesc\",\"ipAddress\":\"127.0.0.1\",\"userAgent\":\"testAgent\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultCodeConstant.CODE_000000))
                .andExpect(jsonPath("$.msg").value(ResultCodeConstant.CODE_000000_MSG));

        verify(operationLogService, times(1)).recordOperationLog(dto);
    }

}