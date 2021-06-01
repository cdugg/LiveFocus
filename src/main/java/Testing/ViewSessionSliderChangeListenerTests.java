package Testing;

import ActionListeners.ViewSessionSliderChangeListener;
import GUI2.ViewSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import static org.mockito.Mockito.*;

public class ViewSessionSliderChangeListenerTests {
    ViewSession mockView;
    JSlider mockSlider;
    ViewSessionSliderChangeListener sliderAction;
    ChangeEvent mockEvent;

    @Before
    public void init(){
        mockView = mock(ViewSession.class);
        mockSlider = new JSlider(1, 99, 50);
        sliderAction = new ViewSessionSliderChangeListener(mockView, mockSlider);
        mockEvent = mock(ChangeEvent.class);
    }

    @Test
    public void ViewSessionSliderChangeListenerTests(){
        //validate sliderAction
        Assert.assertEquals(mockView.displacementToAdd, 0);
        sliderAction.stateChanged(mockEvent);
        Assert.assertEquals(mockView.displacementToAdd, mockSlider.getValue());
    }
}
