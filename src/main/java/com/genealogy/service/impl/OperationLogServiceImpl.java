package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.UserPermissionDTO;
import com.genealogy.entity.OperationLog;
import com.genealogy.entity.SysUser;
import com.genealogy.query.OperationLogQuery;
import com.genealogy.dao.OperationLogDao;
import com.genealogy.dao.SysUserDao;
import com.genealogy.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *   操作日志服务层实现
 * </p>
 * @author xiongyou
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public RestResult<List<UserPermissionDTO>> queryOperationLogs(OperationLogQuery operationLogQuery) {
        List<OperationLog> operationLogs = operationLogDao.findByConditions(
                operationLogQuery.getUserId(),
                operationLogQuery.getModule(),
                operationLogQuery.getStartTime(),
                operationLogQuery.getEndTime()
        );

        List<UserPermissionDTO> result = operationLogs.stream()
                .map(log -> {
                    UserPermissionDTO dto = new UserPermissionDTO();
                    dto.setUserId(log.getUserId());
                    dto.setModule(log.getOperationModule());
                    dto.setPermission(log.getOperationType());
                    dto.setDesc(log.getOperationDesc());
                    dto.setIpAddress(log.getIpAddress());
                    dto.setUserAgent(log.getUserAgent());
                    return dto;
                })
                .collect(Collectors.toList());

        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, result);
    }

    @Override
    @Transactional
    public RestResult<Void> recordOperationLog(UserPermissionDTO userPermissionDTO) {
        Long userId = userPermissionDTO.getUserId();
        String module = userPermissionDTO.getModule();
        String type = userPermissionDTO.getPermission();
        String desc = userPermissionDTO.getDesc();
        String ipAddress = userPermissionDTO.getIpAddress();
        String userAgent = userPermissionDTO.getUserAgent();

        Optional<SysUser> userOptional = sysUserDao.findById(userId);
        if (!userOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.CODE_000001_MSG);
        }

        OperationLog operationLog = new OperationLog();
        operationLog.setUserId(userId);
        operationLog.setOperationModule(module);
        operationLog.setOperationType(type);
        operationLog.setOperationDesc(desc);
        operationLog.setOperationTime(LocalDateTime.now());
        operationLog.setIpAddress(ipAddress);
        operationLog.setUserAgent(userAgent);
        operationLog.setIsDeleted(0);

        operationLogDao.save(operationLog);

        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG);
    }

}