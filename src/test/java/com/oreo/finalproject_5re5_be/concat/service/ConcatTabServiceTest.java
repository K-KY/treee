//package com.oreo.finalproject_5re5_be.concat.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//import com.oreo.finalproject_5re5_be.concat.dto.request.ConcatUpdateRequestDto;
//import com.oreo.finalproject_5re5_be.concat.dto.response.ConcatTabResponseDto;
//import com.oreo.finalproject_5re5_be.concat.repository.ConcatTabRepository;
//import com.oreo.finalproject_5re5_be.concat.service.helper.ConcatTabHelper;
//import com.oreo.finalproject_5re5_be.member.entity.Member;
//import com.oreo.finalproject_5re5_be.project.repository.ProjectRepository;
//import com.oreo.finalproject_5re5_be.project.entity.Project;
//import com.oreo.finalproject_5re5_be.concat.entity.ConcatTab;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//class ConcatTabServiceTest {
//
//    @Mock
//    private ConcatTabRepository concatTabRepository;
//
//    @Mock
//    private ProjectRepository projectRepository;
//
//    @Mock
//    private ConcatTabHelper concatTabHelper;
//
//    @InjectMocks
//    private ConcatTabService concatTabService;
//
//    @Test
//    @DisplayName("ConcatTab 생성 성공한다.")
//    void createConcatTab_Success() {
//        // given : 아이디가 1인 프로젝트 리턴, 컨캣탭이 존재하지 않음
//        Project project = Project.builder().proSeq(1L).build();
//
//        when(projectRepository.findById(project.getProSeq())).thenReturn(Optional.of(project));
//        when(concatTabRepository.existsById(project.getProSeq())).thenReturn(false);
//
//        // when : 컨캣탭 생성
//        boolean result = concatTabService.createConcatTab(project.getProSeq());
//
//        // then : 생성  성공
//        assertThat(result).isTrue();
//        verify(concatTabRepository, times(1)).save(any(ConcatTab.class));//특정 동작이 발생했는지 확인
//        verify(projectRepository, times(1)).findById(project.getProSeq());
//    }
//
//    @Test
//    @DisplayName("프로젝트를 찾을수 없으면 실패한다.")
//    void createConcatTab_Fail_ProjectNotFound() {
//        // given : 프로젝트를 찾을수 없음
//        long projectSeq = 1L;
//
//        when(projectRepository.findById(projectSeq)).thenReturn(Optional.empty());
//
//        // when : 프로젝트를 찾지 못하면
//        boolean result = concatTabService.createConcatTab(projectSeq);
//
//        // then : 실패
//        assertThat(result).isFalse();
//        verify(concatTabRepository, never()).save(any(ConcatTab.class));//프로젝트를 찾지 못했으므로 리포지토리는 실행되지 않는다.
//    }
//
//    @Test
//    @DisplayName("ConcatTab이 이미 존재한다면 실패한다.")
//    void createConcatTab_Fail_ConcatTabExists() {
//        // given : 이미 ConcatTab이 존재
//        Project project = Project.builder().proSeq(1L).build();
//
//        when(projectRepository.findById(project.getProSeq())).thenReturn(Optional.of(project));
//        when(concatTabRepository.existsById(project.getProSeq())).thenReturn(true);
//
//        // when : ConcatTab이 이미 존재
//        boolean result = concatTabService.createConcatTab(project.getProSeq());
//
//        // then : 실패
//        assertThat(result).isFalse();
//        verify(concatTabRepository, never()).save(any(ConcatTab.class));
//    }
//
//    @Test
//    void readConcatTab_Success() {
//        //given : 프로젝트와 ConcatTab이 존재
//        Project project = Project.builder().proSeq(1L).build();
//        String memberSeq = "memberSeq";
//        ConcatTab concatTab = new ConcatTab(1L, project, null, 'Y', 0.5f);
//
//        when(projectRepository.findById(project.getProSeq())).thenReturn(Optional.of(project));
//        when(concatTabRepository.findById(project.getProSeq())).thenReturn(Optional.of(concatTab));
//        when(concatTabHelper.prepareConcatTab(concatTab, memberSeq)).thenReturn(new ConcatTabResponseDto(
//                concatTab.getProjectId(), concatTab.getOption(), concatTab.getFrontSilence(), concatTab.getStatus()
//        ));
//
//        //when : ConcatTab 조회
//        ConcatTabResponseDto result = concatTabService.readConcatTab(project.getProSeq(), memberSeq);
//
//        // then : 성공
//        assertThat(result).isNotNull();
//        verify(projectRepository, times(1)).findById(project.getProSeq());
//        verify(concatTabRepository, times(1)).findById(project.getProSeq());
//    }
//
//    @Test
//    void readConcatTab_CreateConcatTabAndReturn() {
//        //given : 프로젝트가 존재하지만 ConcatTab이 존재하지 않음
//        Project project = Project.builder().proSeq(1L).build();
//        ConcatTab concatTab = new ConcatTab(1L, project, null, 'Y', 0.5f);
//        String memberSeq = "memberSeq";
//
//        when(projectRepository.findById(project.getProSeq())).thenReturn(Optional.of(project));
//        when(concatTabRepository.findById(project.getProSeq()))
//                .thenReturn(Optional.empty())//첫 번째 리턴
//                .thenReturn(Optional.of(concatTab));// 두 번쨰 리턴
//        when(concatTabHelper.prepareConcatTab(concatTab, memberSeq)).thenReturn(new ConcatTabResponseDto(
//                concatTab.getProjectId(), concatTab.getOption(), concatTab.getFrontSilence(), concatTab.getStatus()
//        ));
//        when(concatTabRepository.existsById(project.getProSeq())).thenReturn(false);
//
//        //when : 컨캣탭이 없다면 재생성 후 다시 조회
//        ConcatTabResponseDto result = concatTabService.readConcatTab(project.getProSeq(), memberSeq);
//
//        //then : 생성된 탭을 조회
//        assertThat(result).isNotNull();
//        verify(projectRepository, times(1)).findById(project.getProSeq());//프로젝트 확인시 조회 1번
//        verify(concatTabRepository, times(2)).findById(project.getProSeq());//요청 받을 때, 재생성 후
//    }
//
//    @Test
//    void updateConcatTab_Success() {
//        //given : member, project, concatTab, concatUpdateRequestDto
//        Member member = Member.builder().id("memberSeq").build();
//        Project project = Project.builder().proSeq(1L).member(member).build();
//        ConcatTab concatTab = new ConcatTab(1L, project, null, 'Y', 0.5f);
//        ConcatUpdateRequestDto updateDto = new ConcatUpdateRequestDto(member,
//                project.getProSeq(),
//                concatTab.getOption(),
//                0.0f,
//                'Y'
//                );
//        //when : 업데이트
//        when(projectRepository.findById(project.getProSeq())).thenReturn(Optional.of(project));
//        when(concatTabRepository.findById(project.getProSeq())).thenReturn(Optional.of(concatTab));
//
//        boolean b = concatTabService.updateConcatTab(updateDto);
//        //then : 성공한다.
//        assertThat(b).isTrue();
//
//        verify(concatTabRepository, times(1)).findById(project.getProSeq());
//        verify(projectRepository, times(1)).findById(project.getProSeq());
//    }
//
//    @Test
//    void UpdateConcatTab_Fail_ProjectNotFound() {
//        //given : 프로젝트가 주어지지 않음
//        Member member = Member.builder().id("memberSeq").build();
//        Project project = Project.builder().proSeq(1L).member(member).build();
//        ConcatTab concatTab = new ConcatTab(1L, project, null, 'Y', 0.5f);
//        ConcatUpdateRequestDto updateDto = new ConcatUpdateRequestDto(member,
//                project.getProSeq(),
//                concatTab.getOption(),
//                0.0f,
//                'Y'
//        );
//
//        //when : 업데이트시 프로젝트를 찾을 수 없으면
//        when(projectRepository.findById(project.getProSeq())).thenReturn(Optional.empty());
//        when(concatTabRepository.findById(project.getProSeq())).thenReturn(Optional.of(concatTab));
//        //then : 실패한다.
//
//
//        boolean b = concatTabService.updateConcatTab(updateDto);
//
//        assertThat(b).isFalse();
//
//        verify(concatTabRepository, times(1)).findById(project.getProSeq());
//        verify(projectRepository, times(1)).findById(project.getProSeq());
//    }
//
//    @Test
//    void updateConcatTab_Fail_MissingProjectOrConcatTab() {
//        // Given
//        long tabId = 1L;
//        Member member = Member.builder().id("memberSeq").build();
//        Project project = Project.builder().proSeq(1L).member(member).build();
//        ConcatTab concatTab = new ConcatTab(1L, project, null, 'Y', 0.5f);
//        ConcatUpdateRequestDto updateDto = new ConcatUpdateRequestDto(member,
//                project.getProSeq(),
//                concatTab.getOption(),
//                0.0f,
//                'Y'
//        );
//
//        when(projectRepository.findById(tabId)).thenReturn(Optional.empty());
//        when(concatTabRepository.findById(tabId)).thenReturn(Optional.empty());
//
//        // When
//        boolean result = concatTabService.updateConcatTab(updateDto);
//
//        // Then
//        assertThat(result).isFalse();
//        verify(concatTabRepository, never()).save(any(ConcatTab.class));//호출되지 않음
//    }
//
//}